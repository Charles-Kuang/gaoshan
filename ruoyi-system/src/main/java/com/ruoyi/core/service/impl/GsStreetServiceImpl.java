package com.ruoyi.core.service.impl;

import java.util.List;
import java.util.Objects;

import com.ruoyi.applet.mapper.GsRegionMapper;
import com.ruoyi.common.utils.AMapUtils;
import com.ruoyi.common.utils.ChineseCharacterUtil;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.core.mapper.GsStreetMapper;
import com.ruoyi.core.domain.GsStreet;
import com.ruoyi.core.service.IGsStreetService;

/**
 * 街路巷管理Service业务层处理
 *
 * @author chenghaiqin
 * @date 2022-12-30
 */
@Service
public class GsStreetServiceImpl implements IGsStreetService
{
    @Autowired
    private GsStreetMapper gsStreetMapper;

    @Autowired
    GsRegionMapper gsRegionMapper;

    /**
     * 查询街路巷管理
     *
     * @param streetId 街路巷管理ID
     * @return 街路巷管理
     */
    @Override
    public GsStreet selectGsStreetById(Long streetId)
    {
        return gsStreetMapper.selectGsStreetById(streetId);
    }

    /**
     * 查询街路巷管理列表
     *
     * @param gsStreet 街路巷管理
     * @return 街路巷管理
     */
    @Override
    public List<GsStreet> selectGsStreetList(GsStreet gsStreet)
    {
        return gsStreetMapper.selectGsStreetList(gsStreet);
    }

    /**
     * 新增街路巷管理
     *
     * @param gsStreet 街路巷管理
     * @return 结果
     */
    @Override
    public int insertGsStreet(GsStreet gsStreet)
    {
        if (gsStreet.getName() != null) {
            double[] doubles = AMapUtils.addressToGPS(gsStreet.getName());
            if (doubles != null) {
                gsStreet.setLng(String.valueOf(doubles[0]));
                gsStreet.setLat(String.valueOf(doubles[1]));
            }
            gsStreet.setFirstLetter(ChineseCharacterUtil.getSpells(gsStreet.getName()));
        }
        if(Objects.equals(gsStreet.getInStorage(), "1")){
            gsStreet.setStorageTime(DateUtils.getNowDate());
            gsStreet.setStorageUser(SecurityUtils.getUsername());
            gsStreet.setStorageUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        }
        gsStreet.setRegion(gsRegionMapper.selectGsRegionByCode(gsStreet.getRegionCode()).getName());
        gsStreet.setTowns(gsRegionMapper.selectGsRegionByCode(gsStreet.getTownsCode()).getName());
        gsStreet.setCommittee(gsRegionMapper.selectGsRegionByCode(gsStreet.getCommitteeCode()).getName());
        gsStreet.setCreateTime(DateUtils.getNowDate());
        gsStreet.setCreateUser(SecurityUtils.getUsername());
        gsStreet.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsStreet.setCreateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return gsStreetMapper.insertGsStreet(gsStreet);
    }

    /**
     * 修改街路巷管理
     *
     * @param gsStreet 街路巷管理
     * @return 结果
     */
    @Override
    public int updateGsStreet(GsStreet gsStreet)
    {
        gsStreet.setRegion(gsRegionMapper.selectGsRegionByCode(gsStreet.getRegionCode()).getName());
        gsStreet.setTowns(gsRegionMapper.selectGsRegionByCode(gsStreet.getTownsCode()).getName());
        gsStreet.setCommittee(gsRegionMapper.selectGsRegionByCode(gsStreet.getCommitteeCode()).getName());
        gsStreet.setLastUpdateTime(DateUtils.getNowDate());
        if(Objects.equals(gsStreet.getInStorage(), "1")){
            gsStreet.setStorageTime(DateUtils.getNowDate());
            gsStreet.setStorageUser(SecurityUtils.getUsername());
            gsStreet.setStorageUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        }
        gsStreet.setLastUpdateUser(SecurityUtils.getUsername());
        gsStreet.setLastUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsStreet.setLastUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return gsStreetMapper.updateGsStreet(gsStreet);
    }

    @Override
    public int updateGsStreetOne(GsStreet gsStreet) {
        gsStreet.setLastUpdateTime(DateUtils.getNowDate());
        gsStreet.setLastUpdateUser(SecurityUtils.getUsername());
        gsStreet.setLastUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsStreet.setLastUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        gsStreet.setAuditUser(SecurityUtils.getUsername());
        gsStreet.setAuditUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsStreet.setAuditUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return gsStreetMapper.updateGsStreet(gsStreet);
    }
    /**
     * 批量删除街路巷管理
     * 
     * @param streetIds 需要删除的街路巷管理ID
     * @return 结果
     */
    @Override
    public int deleteGsStreetByIds(Long[] streetIds)
    {
        return gsStreetMapper.deleteGsStreetByIds(streetIds);
    }

    /**
     * 删除街路巷管理信息
     * 
     * @param streetId 街路巷管理ID
     * @return 结果
     */
    @Override
    public int deleteGsStreetById(Long streetId)
    {
        return gsStreetMapper.deleteGsStreetById(streetId);
    }
}
