package com.ruoyi.applet.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ruoyi.applet.mapper.GsRegionMapper;
import com.ruoyi.common.enums.OPERATION_TYPE_ENUM;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.IdCardUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.journal.domain.GsOperationRecord;
import com.ruoyi.journal.service.OperationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.applet.mapper.GsPersonMapper;
import com.ruoyi.applet.domain.GsPerson;
import com.ruoyi.applet.service.IGsPersonService;

import javax.annotation.Resource;

/**
 * 实有人口登记Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-26
 */
@Service
public class GsPersonServiceImpl implements IGsPersonService 
{
    @Resource
    private GsPersonMapper gsPersonMapper;
    
    @Resource
    GsRegionMapper gsRegionMapper;
    @Autowired
    OperationRecordService operationRecordService;
    /**
     * 查询实有人口登记
     * 
     * @param personId 实有人口登记ID
     * @return 实有人口登记
     */
    @Override
    public GsPerson selectGsPersonById(Long personId)
    {
        return gsPersonMapper.selectGsPersonById(personId);
    }

    /**
     * 查询实有人口登记列表
     * 
     * @param gsPerson 实有人口登记
     * @return 实有人口登记
     */
    @Override
    public List<GsPerson> selectGsPersonList(GsPerson gsPerson)
    {
        return gsPersonMapper.selectGsPersonList(gsPerson);
    }

    /**
     * 新增实有人口登记
     * 
     * @param gsPerson 实有人口登记
     * @return 结果
     */
    @Override
    public int insertGsPerson(GsPerson gsPerson)
    {
        if(gsPerson.getIdentityNumber().length()==18){
            gsPerson.setBirthday(IdCardUtils.getBirthdayByIdcard(gsPerson.getIdentityNumber()));
            gsPerson.setAge((long) IdCardUtils.getAgeByIdcard(gsPerson.getIdentityNumber()));
            gsPerson.setSex(IdCardUtils.getSexByIdcard(gsPerson.getIdentityNumber()));
        }
        gsPerson.setCreateTime(DateUtils.getNowDate());
        gsPerson.setCreateUser(SecurityUtils.getUsername());
        gsPerson.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsPerson.setCreateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return gsPersonMapper.insertGsPerson(gsPerson);
    }

    /**
     * 修改实有人口登记
     * 
     * @param gsPerson 实有人口登记
     * @return 结果
     */
    @Override
    public int updateGsPerson(GsPerson gsPerson)
    {
        if(gsPerson.getIdentityNumber().length()==18) {
            gsPerson.setBirthday(IdCardUtils.getBirthdayByIdcard(gsPerson.getIdentityNumber()));
            gsPerson.setAge((long) IdCardUtils.getAgeByIdcard(gsPerson.getIdentityNumber()));
            gsPerson.setSex(IdCardUtils.getSexByIdcard(gsPerson.getIdentityNumber()));
        }
        gsPerson.setLastUpdateTime(DateUtils.getNowDate());
        gsPerson.setLastUpdateUser(SecurityUtils.getUsername());
        gsPerson.setLastUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsPerson.setLastUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());

        GsOperationRecord operationRecord=new GsOperationRecord();
        GsPerson prevDataHouse=gsPersonMapper.selectGsPersonById(gsPerson.getPersonId());
        operationRecord.setPrevData(JSON.toJSONString(prevDataHouse));
        int updateInt=gsPersonMapper.updateGsPerson(gsPerson);
        //增加操作记录
        operationRecord.setLaterData(JSON.toJSONString(gsPerson));
        operationRecord.setOperationType(OPERATION_TYPE_ENUM.PERSON.getValue());
        operationRecord.setDataId(gsPerson.getPersonId());
        operationRecordService.addOperationRecord(operationRecord,null);

        return updateInt;
    }

    /**
     * 批量删除实有人口登记
     * 
     * @param personIds 需要删除的实有人口登记ID
     * @return 结果
     */
    @Override
    public int deleteGsPersonByIds(Long[] personIds)
    {
        return gsPersonMapper.deleteGsPersonByIds(personIds);
    }

    /**
     * 删除实有人口登记信息
     * 
     * @param personId 实有人口登记ID
     * @return 结果
     */
    @Override
    public int deleteGsPersonById(Long personId)
    {
        return gsPersonMapper.deleteGsPersonById(personId);
    }

    @Override
    public int updateGsPersonOne(GsPerson gsPerson) {
        if(gsPerson.getCommitStatus()!=1){
            gsPerson.setLastUpdateTime(DateUtils.getNowDate());
            gsPerson.setLastUpdateUser(SecurityUtils.getUsername());
            gsPerson.setLastUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
            gsPerson.setLastUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
            gsPerson.setAuditUser(SecurityUtils.getUsername());
            gsPerson.setAuditUserId(SecurityUtils.getLoginUser().getUser().getUserId());
            gsPerson.setAuditUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
            gsPerson.setAuditState(3L);
            return gsPersonMapper.updateGsPerson(gsPerson);
        }
        return 0;
    }
}
