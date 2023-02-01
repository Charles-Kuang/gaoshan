package com.ruoyi.applet.service.impl;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.ruoyi.applet.domain.GsPerson;
import com.ruoyi.applet.mapper.GsRegionMapper;
import com.ruoyi.common.core.domain.entity.GsRegion;
import com.ruoyi.common.enums.OPERATION_TYPE_ENUM;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.journal.domain.GsOperationRecord;
import com.ruoyi.journal.service.IGsOperationRecordService;
import com.ruoyi.journal.service.OperationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.applet.mapper.GsHouseMapper;
import com.ruoyi.applet.domain.GsHouse;
import com.ruoyi.applet.service.IGsHouseService;

/**
 * 实有房屋Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-26
 */
@Service
public class GsHouseServiceImpl implements IGsHouseService {
    @Autowired
    private GsHouseMapper gsHouseMapper;

    @Autowired
    GsRegionMapper gsRegionMapper;
    @Autowired
    OperationRecordService operationRecordService;
    /**
     * 查询实有房屋
     *
     * @param houseId 实有房屋ID
     * @return 实有房屋
     */
    @Override
    public GsHouse selectGsHouseById(Long houseId) {
        return gsHouseMapper.selectGsHouseById(houseId);
    }

    /**
     * 查询实有房屋列表
     *
     * @param gsHouse 实有房屋
     * @return 实有房屋
     */
    @Override
    public List<GsHouse> selectGsHouseList(GsHouse gsHouse) {
        return gsHouseMapper.selectGsHouseList(gsHouse);
    }

    /**
     * 新增实有房屋
     *
     * @param gsHouse 实有房屋
     * @return 结果
     */
    @Override
    public int insertGsHouse(GsHouse gsHouse) {
        GsRegion gsRegion = gsRegionMapper.selectGsRegionByCode(gsHouse.getAddressId());
        if (gsRegion != null && gsHouse.getDetailAddress() != null) {
            gsHouse.setAddressDetail(gsRegion.getFullName() + gsHouse.getDetailAddress());
        }
        gsHouse.setCreateTime(DateUtils.getNowDate());
        gsHouse.setCreateUser(SecurityUtils.getUsername());
        gsHouse.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsHouse.setCreateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return gsHouseMapper.insertGsHouse(gsHouse);
    }

    /**
     * 修改实有房屋
     *
     * @param gsHouse 实有房屋
     * @return 结果
     */
    @Override
    public int updateGsHouse(GsHouse gsHouse) {
        GsRegion gsRegionOne = gsRegionMapper.selectGsRegionByCode(gsHouse.getAddressId());
        if (gsRegionOne != null && gsRegionOne.getFullName() != null && gsHouse.getDetailAddress() != null) {
            gsHouse.setAddressDetail(gsRegionOne.getFullName() + gsHouse.getDetailAddress());
        }
        GsOperationRecord operationRecord=new GsOperationRecord();
        GsHouse prevDataHouse=gsHouseMapper.selectGsHouseById(gsHouse.getHouseId());
        operationRecord.setPrevData(JSON.toJSONString(prevDataHouse));

        int updateInt=gsHouseMapper.updateGsHouse(gsHouse);
        //增加操作记录
        operationRecord.setLaterData(JSON.toJSONString(gsHouse));
        operationRecord.setOperationType(OPERATION_TYPE_ENUM.HOUSE.getValue());
        operationRecord.setDataId(gsHouse.getHouseId());
        operationRecordService.addOperationRecord(operationRecord,null);

        gsHouse.setLastUpdateTime(DateUtils.getNowDate());
        gsHouse.setLastUpdateUser(SecurityUtils.getUsername());
        gsHouse.setLastUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsHouse.setLastUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());

        return updateInt;
    }

    @Override
    public int updateGsHouseOne(GsHouse gsHouse) {
        if (gsHouse.getCommitStatus() != 1) {
            gsHouse.setLastUpdateTime(DateUtils.getNowDate());
            gsHouse.setLastUpdateUser(SecurityUtils.getUsername());
            gsHouse.setLastUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
            gsHouse.setLastUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
            gsHouse.setAuditUser(SecurityUtils.getUsername());
            gsHouse.setAuditUserId(SecurityUtils.getLoginUser().getUser().getUserId());
            gsHouse.setAuditUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
            gsHouse.setAuditState(3L);
            return gsHouseMapper.updateGsHouse(gsHouse);
        }
        return 0;
    }

    /**
     * 批量删除实有房屋
     *
     * @param houseIds 需要删除的实有房屋ID
     * @return 结果
     */
    @Override
    public int deleteGsHouseByIds(Long[] houseIds) {
        return gsHouseMapper.deleteGsHouseByIds(houseIds);
    }

    /**
     * 删除实有房屋信息
     *
     * @param houseId 实有房屋ID
     * @return 结果
     */
    @Override
    public int deleteGsHouseById(Long houseId) {
        return gsHouseMapper.deleteGsHouseById(houseId);
    }
}
