package com.ruoyi.applet.mapper;

import java.util.List;
import com.ruoyi.applet.domain.GsUnit;

/**
 * 实有单位列Mapper接口
 * 
 * @author ruoyi
 * @date 2022-11-26
 */
public interface GsUnitMapper
{
    /**
     * 查询实有单位列
     * 
     * @param unitId 实有单位列ID
     * @return 实有单位列
     */
    public GsUnit selectGsUnitById(Long unitId);

    /**
     * 查询实有单位列列表
     * 
     * @param gsUnit 实有单位列
     * @return 实有单位列集合
     */
    public List<GsUnit> selectGsUnitList(GsUnit gsUnit);

    /**
     * 新增实有单位列
     * 
     * @param gsUnit 实有单位列
     * @return 结果
     */
    public int insertGsUnit(GsUnit gsUnit);

    /**
     * 修改实有单位列
     * 
     * @param gsUnit 实有单位列
     * @return 结果
     */
    public int updateGsUnit(GsUnit gsUnit);

    /**
     * 删除实有单位列
     * 
     * @param unitId 实有单位列ID
     * @return 结果
     */
    public int deleteGsUnitById(String unitId);

    /**
     * 批量删除实有单位列
     * 
     * @param unitIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteGsUnitByIds(String[] unitIds);
}
