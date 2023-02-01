package com.ruoyi.applet.mapper;

import java.util.List;
import com.ruoyi.applet.domain.GsHouse;

/**
 * 实有房屋Mapper接口
 * 
 * @author ruoyi
 * @date 2022-11-26
 */
public interface GsHouseMapper
{
    /**
     * 查询实有房屋
     * 
     * @param houseId 实有房屋ID
     * @return 实有房屋
     */
    public GsHouse selectGsHouseById(Long houseId);

    /**
     * 查询实有房屋列表
     * 
     * @param gsHouse 实有房屋
     * @return 实有房屋集合
     */
    public List<GsHouse> selectGsHouseList(GsHouse gsHouse);

    /**
     * 新增实有房屋
     * 
     * @param gsHouse 实有房屋
     * @return 结果
     */
    public int insertGsHouse(GsHouse gsHouse);

    /**
     * 修改实有房屋
     * 
     * @param gsHouse 实有房屋
     * @return 结果
     */
    public int updateGsHouse(GsHouse gsHouse);

    /**
     * 删除实有房屋
     * 
     * @param houseId 实有房屋ID
     * @return 结果
     */
    public int deleteGsHouseById(Long houseId);

    /**
     * 批量删除实有房屋
     * 
     * @param houseIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteGsHouseByIds(Long[] houseIds);
}
