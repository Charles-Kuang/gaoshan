package com.ruoyi.applet.mapper;

import java.util.List;
import com.ruoyi.common.core.domain.entity.GsAddressHouse;

/**
 * addressHouseMapper接口
 * 
 * @author ruoyi
 * @date 2022-12-12
 */
public interface GsAddressHouseMapper
{
    /**
     * 查询addressHouse
     * 
     * @param id addressHouseID
     * @return addressHouse
     */
    public GsAddressHouse selectGsAddressHouseById(Long id);

    /**
     * 查询addressHouse列表
     * 
     * @param gsAddressHouse addressHouse
     * @return addressHouse集合
     */
    public List<GsAddressHouse> selectGsAddressHouseList(GsAddressHouse gsAddressHouse);

    /**
     * 新增addressHouse
     * 
     * @param gsAddressHouse addressHouse
     * @return 结果
     */
    public int insertGsAddressHouse(GsAddressHouse gsAddressHouse);

    /**
     * 修改addressHouse
     * 
     * @param gsAddressHouse addressHouse
     * @return 结果
     */
    public int updateGsAddressHouse(GsAddressHouse gsAddressHouse);

    /**
     * 删除addressHouse
     * 
     * @param id addressHouseID
     * @return 结果
     */
    public int deleteGsAddressHouseById(Long id);

    /**
     * 批量删除addressHouse
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGsAddressHouseByIds(Long[] ids);
}
