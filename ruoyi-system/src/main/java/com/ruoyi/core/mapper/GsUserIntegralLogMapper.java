package com.ruoyi.core.mapper;

import com.ruoyi.core.domain.GsUserIntegralLog;

import java.util.List;

/**
 * 积分记录Mapper接口
 *
 * @author ruoyi
 * @date 2022-12-01
 */
public interface GsUserIntegralLogMapper
{
    /**
     * 查询积分记录
     *
     * @param id 积分记录ID
     * @return 积分记录
     */
    public GsUserIntegralLog selectGsUserIntegralLogById(String id);

    /**
     * 查询积分记录列表
     *
     * @param gsUserIntegralLog 积分记录
     * @return 积分记录集合
     */
    public List<GsUserIntegralLog> selectGsUserIntegralLogList(GsUserIntegralLog gsUserIntegralLog);

    /**
     * 新增积分记录
     *
     * @param gsUserIntegralLog 积分记录
     * @return 结果
     */
    public int insertGsUserIntegralLog(GsUserIntegralLog gsUserIntegralLog);

    /**
     * 修改积分记录
     *
     * @param gsUserIntegralLog 积分记录
     * @return 结果
     */
    public int updateGsUserIntegralLog(GsUserIntegralLog gsUserIntegralLog);

    /**
     * 删除积分记录
     *
     * @param id 积分记录ID
     * @return 结果
     */
    public int deleteGsUserIntegralLogById(String id);

    /**
     * 批量删除积分记录
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGsUserIntegralLogByIds(String[] ids);
}
