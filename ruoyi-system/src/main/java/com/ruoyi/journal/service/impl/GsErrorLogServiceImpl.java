package com.ruoyi.journal.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.journal.mapper.GsErrorLogMapper;
import com.ruoyi.journal.domain.GsErrorLog;
import com.ruoyi.journal.service.IGsErrorLogService;

/**
 * 错误日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@Service
public class GsErrorLogServiceImpl implements IGsErrorLogService 
{
    @Autowired
    private GsErrorLogMapper gsErrorLogMapper;

    /**
     * 查询错误日志
     * 
     * @param id 错误日志ID
     * @return 错误日志
     */
    @Override
    public GsErrorLog selectGsErrorLogById(Long id)
    {
        return gsErrorLogMapper.selectGsErrorLogById(id);
    }

    /**
     * 查询错误日志列表
     * 
     * @param gsErrorLog 错误日志
     * @return 错误日志
     */
    @Override
    public List<GsErrorLog> selectGsErrorLogList(GsErrorLog gsErrorLog)
    {
        return gsErrorLogMapper.selectGsErrorLogList(gsErrorLog);
    }

    /**
     * 新增错误日志
     * 
     * @param gsErrorLog 错误日志
     * @return 结果
     */
    @Override
    public int insertGsErrorLog(GsErrorLog gsErrorLog)
    {
        gsErrorLog.setCreateTime(DateUtils.getNowDate());
        return gsErrorLogMapper.insertGsErrorLog(gsErrorLog);
    }

    /**
     * 修改错误日志
     * 
     * @param gsErrorLog 错误日志
     * @return 结果
     */
    @Override
    public int updateGsErrorLog(GsErrorLog gsErrorLog)
    {
        return gsErrorLogMapper.updateGsErrorLog(gsErrorLog);
    }

    /**
     * 批量删除错误日志
     * 
     * @param ids 需要删除的错误日志ID
     * @return 结果
     */
    @Override
    public int deleteGsErrorLogByIds(Long[] ids)
    {
        return gsErrorLogMapper.deleteGsErrorLogByIds(ids);
    }

    /**
     * 删除错误日志信息
     * 
     * @param id 错误日志ID
     * @return 结果
     */
    @Override
    public int deleteGsErrorLogById(Long id)
    {
        return gsErrorLogMapper.deleteGsErrorLogById(id);
    }
}
