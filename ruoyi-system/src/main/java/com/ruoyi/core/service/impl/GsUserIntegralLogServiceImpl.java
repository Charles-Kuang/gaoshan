package com.ruoyi.core.service.impl;

import com.ruoyi.core.domain.GsUserIntegralLog;
import com.ruoyi.core.mapper.GsUserIntegralLogMapper;
import com.ruoyi.core.service.IGsUserIntegralLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 积分记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-12-02
 */
@Service
public class GsUserIntegralLogServiceImpl implements IGsUserIntegralLogService
{
    @Autowired
    private GsUserIntegralLogMapper gsUserIntegralLogMapper;

    /**
     * 查询积分记录
     *
     * @param id 积分记录ID
     * @return 积分记录
     */
    @Override
    public GsUserIntegralLog selectGsUserIntegralLogById(String id)
    {
        return gsUserIntegralLogMapper.selectGsUserIntegralLogById(id);
    }

    /**
     * 查询积分记录列表
     *
     * @param gsUserIntegralLog 积分记录
     * @return 积分记录
     */
    @Override
    public List<GsUserIntegralLog> selectGsUserIntegralLogList(GsUserIntegralLog gsUserIntegralLog)
    {
        return gsUserIntegralLogMapper.selectGsUserIntegralLogList(gsUserIntegralLog);
    }

    /**
     * 新增积分记录
     *
     * @param gsUserIntegralLog 积分记录
     * @return 结果
     */
    @Override
    public int insertGsUserIntegralLog(GsUserIntegralLog gsUserIntegralLog)
    {
        return gsUserIntegralLogMapper.insertGsUserIntegralLog(gsUserIntegralLog);
    }

    /**
     * 修改积分记录
     *
     * @param gsUserIntegralLog 积分记录
     * @return 结果
     */
    @Override
    public int updateGsUserIntegralLog(GsUserIntegralLog gsUserIntegralLog)
    {
        return gsUserIntegralLogMapper.updateGsUserIntegralLog(gsUserIntegralLog);
    }

    /**
     * 批量删除积分记录
     *
     * @param ids 需要删除的积分记录ID
     * @return 结果
     */
    @Override
    public int deleteGsUserIntegralLogByIds(String[] ids)
    {
        return gsUserIntegralLogMapper.deleteGsUserIntegralLogByIds(ids);
    }

    /**
     * 删除积分记录信息
     *
     * @param id 积分记录ID
     * @return 结果
     */
    @Override
    public int deleteGsUserIntegralLogById(String id)
    {
        return gsUserIntegralLogMapper.deleteGsUserIntegralLogById(id);
    }
}
