package com.ruoyi.applet.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.applet.mapper.GsRegionDetailMapper;
import com.ruoyi.applet.domain.GsRegionDetail;
import com.ruoyi.applet.service.IGsRegionDetailService;

/**
 * 标准详细地址Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@Service
public class GsRegionDetailServiceImpl implements IGsRegionDetailService 
{
    @Autowired
    private GsRegionDetailMapper gsRegionDetailMapper;

    /**
     * 查询标准详细地址
     * 
     * @param addressId 标准详细地址ID
     * @return 标准详细地址
     */
    @Override
    public GsRegionDetail selectGsRegionDetailById(Long addressId)
    {
        return gsRegionDetailMapper.selectGsRegionDetailById(addressId);
    }

    /**
     * 查询标准详细地址列表
     * 
     * @param gsRegionDetail 标准详细地址
     * @return 标准详细地址
     */
    @Override
    public List<GsRegionDetail> selectGsRegionDetailList(GsRegionDetail gsRegionDetail)
    {
        return gsRegionDetailMapper.selectGsRegionDetailList(gsRegionDetail);
    }

    /**
     * 新增标准详细地址
     * 
     * @param gsRegionDetail 标准详细地址
     * @return 结果
     */
    @Override
    public int insertGsRegionDetail(GsRegionDetail gsRegionDetail)
    {
        gsRegionDetail.setCreateTime(DateUtils.getNowDate());
        return gsRegionDetailMapper.insertGsRegionDetail(gsRegionDetail);
    }

    /**
     * 修改标准详细地址
     * 
     * @param gsRegionDetail 标准详细地址
     * @return 结果
     */
    @Override
    public int updateGsRegionDetail(GsRegionDetail gsRegionDetail)
    {
        return gsRegionDetailMapper.updateGsRegionDetail(gsRegionDetail);
    }

    /**
     * 批量删除标准详细地址
     * 
     * @param addressIds 需要删除的标准详细地址ID
     * @return 结果
     */
    @Override
    public int deleteGsRegionDetailByIds(Long[] addressIds)
    {
        return gsRegionDetailMapper.deleteGsRegionDetailByIds(addressIds);
    }

    /**
     * 删除标准详细地址信息
     * 
     * @param addressId 标准详细地址ID
     * @return 结果
     */
    @Override
    public int deleteGsRegionDetailById(Long addressId)
    {
        return gsRegionDetailMapper.deleteGsRegionDetailById(addressId);
    }
}
