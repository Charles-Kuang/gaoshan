package com.ruoyi.journal.mapper;

import java.util.List;
import com.ruoyi.journal.domain.GsErrorLog;

/**
 * 错误日志Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public interface GsErrorLogMapper
{
    /**
     * 查询错误日志
     * 
     * @param id 错误日志ID
     * @return 错误日志
     */
    public GsErrorLog selectGsErrorLogById(Long id);

    /**
     * 查询错误日志列表
     * 
     * @param gsErrorLog 错误日志
     * @return 错误日志集合
     */
    public List<GsErrorLog> selectGsErrorLogList(GsErrorLog gsErrorLog);

    /**
     * 新增错误日志
     * 
     * @param gsErrorLog 错误日志
     * @return 结果
     */
    public int insertGsErrorLog(GsErrorLog gsErrorLog);

    /**
     * 修改错误日志
     * 
     * @param gsErrorLog 错误日志
     * @return 结果
     */
    public int updateGsErrorLog(GsErrorLog gsErrorLog);

    /**
     * 删除错误日志
     * 
     * @param id 错误日志ID
     * @return 结果
     */
    public int deleteGsErrorLogById(Long id);

    /**
     * 批量删除错误日志
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteGsErrorLogByIds(Long[] ids);
}
