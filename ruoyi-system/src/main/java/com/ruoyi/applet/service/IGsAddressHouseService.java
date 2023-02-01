package com.ruoyi.applet.service;

import java.io.IOException;
import java.util.List;
import com.ruoyi.common.core.domain.entity.GsAddressHouse;

import javax.servlet.http.HttpServletResponse;

/**
 * addressHouseService接口
 * 
 * @author ruoyi
 * @date 2022-12-12
 */
public interface IGsAddressHouseService 
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
     * 批量删除addressHouse
     * 
     * @param ids 需要删除的addressHouseID
     * @return 结果
     */
    public int deleteGsAddressHouseByIds(Long[] ids);

    /**
     * 删除addressHouse信息
     * 
     * @param id addressHouseID
     * @return 结果
     */
    public int deleteGsAddressHouseById(Long id);

    int updateGsAddressHouseOne(GsAddressHouse gsAddressHouse);

    byte[] createQRCode(Long number, HttpServletResponse response) throws IOException;
}
