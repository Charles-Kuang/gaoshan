package com.ruoyi.core.service;

import java.util.List;
import com.ruoyi.core.domain.GsStreet;

/**
 * 街路巷管理Service接口
 * 
 * @author chenghaiqin
 * @date 2022-12-30
 */
public interface IGsStreetService 
{
    /**
     * 查询街路巷管理
     * 
     * @param streetId 街路巷管理ID
     * @return 街路巷管理
     */
    public GsStreet selectGsStreetById(Long streetId);

    /**
     * 查询街路巷管理列表
     * 
     * @param gsStreet 街路巷管理
     * @return 街路巷管理集合
     */
    public List<GsStreet> selectGsStreetList(GsStreet gsStreet);

    /**
     * 新增街路巷管理
     * 
     * @param gsStreet 街路巷管理
     * @return 结果
     */
    public int insertGsStreet(GsStreet gsStreet);

    /**
     * 修改街路巷管理
     * 
     * @param gsStreet 街路巷管理
     * @return 结果
     */
    public int updateGsStreet(GsStreet gsStreet);

    /**
     * 批量删除街路巷管理
     * 
     * @param streetIds 需要删除的街路巷管理ID
     * @return 结果
     */
    public int deleteGsStreetByIds(Long[] streetIds);

    /**
     * 删除街路巷管理信息
     * 
     * @param streetId 街路巷管理ID
     * @return 结果
     */
    public int deleteGsStreetById(Long streetId);

    int updateGsStreetOne(GsStreet gsStreet);
}
