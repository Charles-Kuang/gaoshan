package com.gaoshan.modules.wechat.operation.record.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.enumeration.TOKEN_TIME_TYPE;
import com.gaoshan.modules.wechat.address.domain.Address;
import com.gaoshan.modules.wechat.enumeration.OPERATION_TYPE_ENUM;
import com.gaoshan.modules.wechat.house.domain.House;
import com.gaoshan.modules.wechat.operation.record.domain.*;
import com.gaoshan.modules.wechat.operation.record.mapping.*;
import com.gaoshan.modules.wechat.operation.record.service.OperationRecordService;
import com.gaoshan.modules.wechat.person.domain.Person;
import com.gaoshan.modules.wechat.unit.domain.Unit;
import com.gaoshan.modules.wechat.user.domain.User;
import com.gaoshan.modules.wechat.util.DomainUtil;
import com.gaoshan.util.TokenUtil;
import com.github.pagehelper.util.StringUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.gaoshan.modules.wechat.enumeration.OPERATION_TYPE_ENUM.HOUSE;
import static com.gaoshan.modules.wechat.enumeration.OPERATION_TYPE_ENUM.PERSON;

@Service
public class OperationRecordServiceImpl extends ServiceImpl<OperationRecordMapper, OperationRecord> implements OperationRecordService {
    @Resource
    private OperationAddressRecordMapper operationAddressRecordMapper;
    @Resource
    private OperationUnitRecordMapper operationUnitRecordMapper;
    @Resource
    private OperationPersonRecordMapper operationPersonRecordMapper;
    @Resource
    private OperationHouseRecordMapper operationHouseRecordMapper;
    @Async
    @Override
    public void addOperationRecord(OperationRecord operationRecord) {
        OPERATION_TYPE_ENUM operationType=OPERATION_TYPE_ENUM.getByValue(operationRecord.getOperationType());
        operationRecord.setOperationType(operationType.getValue());

        User user=TokenUtil.getTokenUser();
        operationRecord.setOperationUser(StringUtil.isEmpty(user.getRealName())?user.getNickName():user.getRealName());
        operationRecord.setOperationUserId(user.getUserId().longValue());
        operationRecord.setOperationUserPhone(user.getPhone());
        operationRecord.setOperationTime(new Date());

        switch (operationType){
            case PERSON:
                Person prevPerson =JSON.parseObject(operationRecord.getPrevData(),Person.class);
                Person nowPerson =JSON.parseObject(operationRecord.getLaterData(),Person.class);
                List<String> diffPerson= DomainUtil.difference(prevPerson,nowPerson);
                if(null!=diffPerson){
                    operationRecord.setPrevUpdateData(diffPerson.get(0));
                    operationRecord.setLaterUpdateData(diffPerson.get(1));
                }
                OperationPersonRecord operationPersonRecord=JSON.parseObject(JSON.toJSONString(operationRecord), OperationPersonRecord.class);
                operationPersonRecordMapper.insert(operationPersonRecord);
                break;
            case HOUSE:
                House prevHouse =JSON.parseObject(operationRecord.getPrevData(), House.class);
                House nowHouse =JSON.parseObject(operationRecord.getLaterData(),House.class);
                List<String> diffHouse= DomainUtil.difference(prevHouse,nowHouse);
                if(null!=diffHouse){
                    operationRecord.setPrevUpdateData(diffHouse.get(0));
                    operationRecord.setLaterUpdateData(diffHouse.get(1));
                }
                OperationHouseRecord operationHouseRecord=JSON.parseObject(JSON.toJSONString(operationRecord), OperationHouseRecord.class);
                operationHouseRecordMapper.insert(operationHouseRecord);
                break;
            case UNIT:
                Unit prevUnit =JSON.parseObject(operationRecord.getPrevData(), Unit.class);
                Unit nowUnit =JSON.parseObject(operationRecord.getLaterData(),Unit.class);
                List<String> diffUnit= DomainUtil.difference(prevUnit,nowUnit);
                if(null!=diffUnit){
                    operationRecord.setPrevUpdateData(diffUnit.get(0));
                    operationRecord.setLaterUpdateData(diffUnit.get(1));
                }
                OperationUnitRecord operationUnitRecord=JSON.parseObject(JSON.toJSONString(operationRecord), OperationUnitRecord.class);
                operationUnitRecordMapper.insert(operationUnitRecord);
                break;
            case ADDRESS:
                Address prevAddress =JSON.parseObject(operationRecord.getPrevData(), Address.class);
                Address nowAddress =JSON.parseObject(operationRecord.getLaterData(),Address.class);
                List<String> diffAddress= DomainUtil.difference(prevAddress,nowAddress);
                if(null!=diffAddress){
                    operationRecord.setPrevUpdateData(diffAddress.get(0));
                    operationRecord.setLaterUpdateData(diffAddress.get(1));
                }
                OperationAddressRecord operationAddressRecord=JSON.parseObject(JSON.toJSONString(operationRecord),OperationAddressRecord.class);
                operationAddressRecordMapper.insert(operationAddressRecord);
            default:
                break;
        }
        //this.getBaseMapper().insert(operationRecord);
    }
}
