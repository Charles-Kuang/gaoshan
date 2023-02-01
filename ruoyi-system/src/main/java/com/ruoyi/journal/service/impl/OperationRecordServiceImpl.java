package com.ruoyi.journal.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.applet.domain.GsHouse;
import com.ruoyi.applet.domain.GsPerson;
import com.ruoyi.applet.domain.GsUnit;
import com.ruoyi.applet.mapper.OperationAddressRecordMapper;
import com.ruoyi.applet.mapper.OperationHouseRecordMapper;
import com.ruoyi.applet.mapper.OperationPersonRecordMapper;
import com.ruoyi.applet.mapper.OperationUnitRecordMapper;
import com.ruoyi.common.core.domain.entity.GsRegion;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.OPERATION_TYPE_ENUM;
import com.ruoyi.common.utils.DomainUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.journal.domain.*;
import com.ruoyi.journal.mapper.GsOperationRecordMapper;
import com.ruoyi.journal.service.OperationRecordService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Service
public class OperationRecordServiceImpl extends ServiceImpl<GsOperationRecordMapper, GsOperationRecord> implements OperationRecordService {
    @Resource
    private OperationAddressRecordMapper operationAddressRecordMapper;
    @Resource
    private OperationUnitRecordMapper operationUnitRecordMapper;
    @Resource
    private OperationPersonRecordMapper operationPersonRecordMapper;
    @Resource
    private OperationHouseRecordMapper operationHouseRecordMapper;
    @Resource
    private GsOperationRecordMapper gsOperationRecordMapper;
    @Async
    @Override
    public void addOperationRecord(GsOperationRecord operationRecord, HttpServletRequest request) {
        OPERATION_TYPE_ENUM operationType=OPERATION_TYPE_ENUM.getByValue(operationRecord.getOperationType());
        assert operationType != null;
        operationRecord.setOperationType(operationType.getValue());

        SysUser user= SecurityUtils.getLoginUser().getUser();
        operationRecord.setOperationUser(StringUtil.isEmpty(user.getUserName())?user.getUserName():user.getNickName());
        operationRecord.setOperationUserId(user.getUserId());
        operationRecord.setOperationUserPhone(user.getPhonenumber());
        operationRecord.setOperationTime(new Date());
        switch (operationType){
            case PERSON:
                GsPerson prevPerson =JSON.parseObject(operationRecord.getPrevData(),GsPerson.class);
                GsPerson nowPerson =JSON.parseObject(operationRecord.getLaterData(),GsPerson.class);
                List<String> diffPerson= DomainUtil.difference(prevPerson,nowPerson);
                if(null!=diffPerson){
                    operationRecord.setPrevUpdateData(diffPerson.get(0));
                    operationRecord.setLaterUpdateData(diffPerson.get(1));
                }
                GsOperationPersonRecord operationPersonRecord=JSON.parseObject(JSON.toJSONString(operationRecord), GsOperationPersonRecord.class);
                operationPersonRecordMapper.insert(operationPersonRecord);
                break;
            case HOUSE:
                GsHouse prevHouse =JSON.parseObject(operationRecord.getPrevData(), GsHouse.class);
                GsHouse nowHouse =JSON.parseObject(operationRecord.getLaterData(),GsHouse.class);
                List<String> diffHouse= DomainUtil.difference(prevHouse,nowHouse);
                if(null!=diffHouse){
                    operationRecord.setPrevUpdateData(diffHouse.get(0));
                    operationRecord.setLaterUpdateData(diffHouse.get(1));
                }
                GsOperationHouseRecord operationHouseRecord=JSON.parseObject(JSON.toJSONString(operationRecord), GsOperationHouseRecord.class);
                operationHouseRecordMapper.insert(operationHouseRecord);
                break;
            case UNIT:
                GsUnit prevUnit =JSON.parseObject(operationRecord.getPrevData(), GsUnit.class);
                GsUnit nowUnit =JSON.parseObject(operationRecord.getLaterData(),GsUnit.class);
                List<String> diffUnit= DomainUtil.difference(prevUnit,nowUnit);
                if(null!=diffUnit){
                    operationRecord.setPrevUpdateData(diffUnit.get(0));
                    operationRecord.setLaterUpdateData(diffUnit.get(1));
                }
                GsOperationUnitRecord operationUnitRecord=JSON.parseObject(JSON.toJSONString(operationRecord), GsOperationUnitRecord.class);
                operationUnitRecordMapper.insert(operationUnitRecord);
                break;
            case ADDRESS:
                GsRegion prevAddress =JSON.parseObject(operationRecord.getPrevData(), GsRegion.class);
                GsRegion nowAddress =JSON.parseObject(operationRecord.getLaterData(),GsRegion.class);
                List<String> diffAddress= DomainUtil.difference(prevAddress,nowAddress);
                if(null!=diffAddress){
                    operationRecord.setPrevUpdateData(diffAddress.get(0));
                    operationRecord.setLaterUpdateData(diffAddress.get(1));
                }
                GsOperationAddressRecord operationAddressRecord=JSON.parseObject(JSON.toJSONString(operationRecord),GsOperationAddressRecord.class);
                operationAddressRecordMapper.insert(operationAddressRecord);
            default:
                Object prevData =JSON.parseObject(operationRecord.getPrevData());
                Object nowData =JSON.parseObject(operationRecord.getLaterData());
                List<String> diffData= DomainUtil.difference(prevData,nowData);
                if(null!=diffData){
                    operationRecord.setPrevUpdateData(diffData.get(0));
                    operationRecord.setLaterUpdateData(diffData.get(1));
                }
                GsOperationRecord gsOperationRecord=JSON.parseObject(JSON.toJSONString(operationRecord), GsOperationRecord.class);
                gsOperationRecordMapper.insertGsOperationRecord(gsOperationRecord);
                break;
        }
        //this.getBaseMapper().insert(operationRecord);
    }
}
