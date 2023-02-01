package com.ruoyi.applet.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.applet.domain.vo.CityRegionVo;
import com.ruoyi.applet.mapper.GsRegionMapper;
import com.ruoyi.applet.service.IGsRegionService;
import com.ruoyi.common.core.domain.entity.GsRegion;
import com.ruoyi.common.utils.*;
import com.ruoyi.core.mapper.GsRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 行政区Service业务层处理
 *
 * @author ruoyi
 * @date 2022-11-14
 */
@Service
public class GsRegionServiceImpl implements IGsRegionService {
    @Autowired
    GsRegionMapper gsRegionMapper;

    @Autowired
    GsRoleMapper gsRoleMapper;

    @Override
    public GsRegion selectGsRegionByCode(Long code) {
        return gsRegionMapper.selectGsRegionByCode(code);
    }

    public String getFullList(GsRegion region) {
        GsRegion ParentRegion = gsRegionMapper.selectGsRegionByCode(region.getParentCode());
        if (ParentRegion != null && region.getParentCode() != 0) {
            region.setFullName(getFullList(ParentRegion) + "," + region.getName());
        } else {
            region.setFullName(region.getName());
        }
        return region.getFullName();
    }

    /**
     * 查询行政区列表
     *
     * @param gsRegion 行政区
     * @return 行政区
     */
    @Override
    public List<GsRegion> selectGsRegionList(GsRegion gsRegion) {
        return gsRegionMapper.selectGsRegionList(gsRegion);
    }

    @Override
    public List<GsRegion> selectGsRegionListOne(GsRegion gsRegion) {
        List<GsRegion> childes = gsRegionMapper.selectGsRegionListOne(gsRegion);
        if (childes.get(0).getLevel() == 3) {
            List<GsRegion> childesOne =new ArrayList<>();
            for (GsRegion region : childes) {
                region.setHasChild(false);
                childesOne.add(region);
            }
            return childesOne;
        }
        return childes;
    }

    /**
     * 新增行政区
     *
     * @param gsRegion 行政区
     * @return 结果
     */
    @Override
    public int insertGsRegion(GsRegion gsRegion) {
        GsRegion gsRegion1 = ChangeGsRegion(gsRegion);
        gsRegion1.setCreateTime(DateUtils.getNowDate());
        gsRegion1.setCreateUser(SecurityUtils.getUsername());
        gsRegion1.setCreateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsRegion1.setCreateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        if (gsRegion.getName() != null) {
            double[] doubles = AMapUtils.addressToGPS(gsRegion.getName());
            if (doubles != null) {
                gsRegion.setLng(String.valueOf(doubles[0]));
                gsRegion.setLat(String.valueOf(doubles[1]));
            }
            gsRegion.setFirstLetter(ChineseCharacterUtil.getSpells(gsRegion.getName()));
        }
        return gsRegionMapper.insertGsRegion(gsRegion1);
    }

    /**
     * 修改行政区
     *
     * @param gsRegion 行政区
     * @return 结果
     */
    @Override
    public int updateGsRegion(GsRegion gsRegion) {
        GsRegion gsRegion1 = ChangeGsRegion(gsRegion);
        gsRegion1.setLastUpdateUser(SecurityUtils.getUsername());
        gsRegion1.setLastUpdateTime(DateUtils.getNowDate());
        gsRegion1.setLastUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        gsRegion1.setLastUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
        return gsRegionMapper.updateGsRegion(gsRegion1);
    }

    /**
     * 修改行政区
     *
     * @param gsRegion 行政区
     * @return 结果
     */
    @Override
    public int updateGsRegionOne(GsRegion gsRegion) {
        if (gsRegion.getCommitStatus() != 1) {
            gsRegion.setLastUpdateUser(SecurityUtils.getUsername());
            gsRegion.setLastUpdateTime(DateUtils.getNowDate());
            gsRegion.setLastUpdateUserId(SecurityUtils.getLoginUser().getUser().getUserId());
            gsRegion.setLastUpdateUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
            gsRegion.setAuditUser(SecurityUtils.getUsername());
            gsRegion.setAuditUserId(SecurityUtils.getLoginUser().getUser().getUserId());
            gsRegion.setAuditUserPhone(SecurityUtils.getLoginUser().getUser().getPhonenumber());
            gsRegion.setAuditState(3L);
            return gsRegionMapper.updateGsRegion(gsRegion);
        }
        return 0;
    }

    public GsRegion ChangeGsRegion(GsRegion gsRegion) {
        if (gsRegion.getParentCode() == null) {
            gsRegion.setParentCode(0L);
            gsRegion.setParentName("中国");
            gsRegion.setFullName(gsRegion.getName());
            gsRegion.setLevel(0);
        } else {
            String fullList = getFullList(gsRegion);
            String[] split = fullList.split(",");
            gsRegion.setLevel(split.length - 1);
            gsRegion.setFullName(fullList.replace(",", ""));
            GsRegion info = gsRegionMapper.selectGsRegionByCode(gsRegion.getParentCode());
            if (info != null && gsRegion.getParentCode() != 0) {
                gsRegion.setForefathers(info.getForefathers() + "," + info.getCode());
                gsRegion.setParentName(info.getName());
                if (info.getHasChild() == null || !info.getHasChild()) {
                    info.setHasChild(true);
                }
                gsRegionMapper.updateGsRegion(info);
            } else {
                gsRegion.setForefathers(gsRegion.getParentCode().toString());
            }
        }
        return gsRegion;
    }

    /**
     * 批量删除行政区
     *
     * @param regionIds 需要删除的行政区ID
     * @return 结果
     */
    @Override
    public int deleteGsRegionByIds(Long[] regionIds) {
        return gsRegionMapper.deleteGsRegionByIds(regionIds);
    }

    @Override
    public List<GsRegion> getChild(Long code) {
        return gsRegionMapper.getChild(code);
    }

    @Override
    public ArrayList<CityRegionVo> selectRegionList() {
        CityRegionVo cityRegionVo = new CityRegionVo();
        cityRegionVo.setCityCode(520000L);
        cityRegionVo.setCityName("贵州省");
        cityRegionVo.setHasChild(true);
        cityRegionVo.setChildren(gsRegionMapper.selectGsRegionListTwo());
        ArrayList<CityRegionVo>  cityRegionVos= new ArrayList<>();
        cityRegionVos.add(cityRegionVo);
        return cityRegionVos;
    }

    @Override
    public List<CityRegionVo> getChildOne(Long code) {
        return gsRegionMapper.getChildOne(code);
    }

    @Override
    public  List<CityRegionVo> listGsRegion(GsRegion region) {
        return gsRegionMapper.listGsRegion(region);
    }

    @Override
    public List<GsRegion> selectGsRegionListTwo(GsRegion gsRegion) {
        List<GsRegion> childes = gsRegionMapper.selectGsRegionListOne(gsRegion);
        if (childes.get(0).getLevel() == 2) {
            List<GsRegion> childesOne =new ArrayList<>();
            for (GsRegion region : childes) {
                region.setHasChild(false);
                childesOne.add(region);
            }
            return childesOne;
        }
        return childes;
    }

    @Override
    public int updateInvalidFlag(GsRegion region) {
        return gsRegionMapper.updateInvalidFlag(region);
    }

}
