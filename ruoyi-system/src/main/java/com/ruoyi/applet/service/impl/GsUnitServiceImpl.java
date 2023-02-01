package com.ruoyi.applet.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ruoyi.applet.domain.GsHouse;
import com.ruoyi.applet.domain.GsPerson;
import com.ruoyi.applet.mapper.GsRegionMapper;
import com.ruoyi.common.core.domain.entity.GsRegion;
import com.ruoyi.common.enums.OPERATION_TYPE_ENUM;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.journal.domain.GsOperationRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.applet.mapper.GsUnitMapper;
import com.ruoyi.applet.domain.GsUnit;
import com.ruoyi.applet.service.IGsUnitService;

import javax.annotation.Resource;

/**
 * 实有单位列Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-26
 */
@Service
public class GsUnitServiceImpl implements IGsUnitService 
{
    @Resource
    private GsUnitMapper gsUnitMapper;

    /**
     * 查询实有单位列
     * 
     * @param unitId 实有单位列ID
     * @return 实有单位列
     */
    @Override
    public GsUnit selectGsUnitById(Long unitId)
    {
        return gsUnitMapper.selectGsUnitById(unitId);
    }

    /**
     * 查询实有单位列列表
     * 
     * @param gsUnit 实有单位列
     * @return 实有单位列
     */
    @Override
    public List<GsUnit> selectGsUnitList(GsUnit gsUnit)
    {
        return gsUnitMapper.selectGsUnitList(gsUnit);
    }

    /**
     * 新增实有单位列
     * 
     * @param gsUnit 实有单位列
     * @return 结果
     */
    @Override
    public int insertGsUnit(GsUnit gsUnit)
    {
        gsUnit.setCreateTime(DateUtils.getNowDate());
        gsUnit.setCreateUser(SecurityUtils.getUsername());
        gsUnit.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsUnit.setCreateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return gsUnitMapper.insertGsUnit(gsUnit);
    }

    /**
     * 修改实有单位列
     * 
     * @param gsUnit 实有单位列
     * @return 结果
     */
    @Override
    public int updateGsUnit(GsUnit gsUnit)
    {
        gsUnit.setLastUpdateTime(DateUtils.getNowDate());
        gsUnit.setLastUpdateUser(SecurityUtils.getUsername());
        gsUnit.setLastUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsUnit.setLastUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());

        GsOperationRecord operationRecord=new GsOperationRecord();
        GsUnit prevDataHouse=gsUnitMapper.selectGsUnitById(gsUnit.getUnitId());
        operationRecord.setPrevData(JSON.toJSONString(prevDataHouse));
        int updateInt=gsUnitMapper.updateGsUnit(gsUnit);
        //增加操作记录
        operationRecord.setLaterData(JSON.toJSONString(gsUnit));
        operationRecord.setOperationType(OPERATION_TYPE_ENUM.UNIT.getValue());
        operationRecord.setDataId(gsUnit.getUnitId());
        return updateInt;
    }

    @Override
    public int updateGsUnitOne(GsUnit gsUnit) {
        if(gsUnit.getCommitStatus()!=1){
            gsUnit.setLastUpdateTime(DateUtils.getNowDate());
            gsUnit.setLastUpdateUser(SecurityUtils.getUsername());
            gsUnit.setLastUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
            gsUnit.setLastUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
            gsUnit.setAuditUser(SecurityUtils.getUsername());
            gsUnit.setAuditUserId(SecurityUtils.getLoginUser().getUser().getUserId());
            gsUnit.setAuditUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
            gsUnit.setAuditState(3L);
            return gsUnitMapper.updateGsUnit(gsUnit);
        }
        return 0;
    }

    /**
     * 批量删除实有单位列
     * 
     * @param unitIds 需要删除的实有单位列ID
     * @return 结果
     */
    @Override
    public int deleteGsUnitByIds(String[] unitIds)
    {
        return gsUnitMapper.deleteGsUnitByIds(unitIds);
    }

    /**
     * 删除实有单位列信息
     * 
     * @param unitId 实有单位列ID
     * @return 结果
     */
    @Override
    public int deleteGsUnitById(String unitId)
    {
        return gsUnitMapper.deleteGsUnitById(unitId);
    }
}
