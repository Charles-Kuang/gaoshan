package com.ruoyi.applet.mapper;

import java.util.List;
import com.ruoyi.applet.domain.GsRegionDetail;

/**
 * 标准详细地址Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public interface GsRegionDetailMapper
{
    /**
     * 查询标准详细地址
     * 
     * @param addressId 标准详细地址ID
     * @return 标准详细地址
     */
    public GsRegionDetail selectGsRegionDetailById(Long addressId);

    /**
     * 查询标准详细地址列表
     * 
     * @param gsRegionDetail 标准详细地址
     * @return 标准详细地址集合
     */
    public List<GsRegionDetail> selectGsRegionDetailList(GsRegionDetail gsRegionDetail);

    /**
     * 新增标准详细地址
     * 
     * @param gsRegionDetail 标准详细地址
     * @return 结果
     */
    public int insertGsRegionDetail(GsRegionDetail gsRegionDetail);

    /**
     * 修改标准详细地址
     * 
     * @param gsRegionDetail 标准详细地址
     * @return 结果
     */
    public int updateGsRegionDetail(GsRegionDetail gsRegionDetail);

    /**
     * 删除标准详细地址
     * 
     * @param addressId 标准详细地址ID
     * @return 结果
     */
    public int deleteGsRegionDetailById(Long addressId);

    /**
     * 批量删除标准详细地址
     * 
     * @param addressIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteGsRegionDetailByIds(Long[] addressIds);
}
