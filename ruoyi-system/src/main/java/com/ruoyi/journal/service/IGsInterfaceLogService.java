package com.ruoyi.journal.service;

import java.util.List;
import com.ruoyi.journal.domain.GsInterfaceLog;

/**
 * 接口日志Service接口
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public interface IGsInterfaceLogService 
{
    /**
     * 查询接口日志
     * 
     * @param id 接口日志ID
     * @return 接口日志
     */
    public GsInterfaceLog selectGsInterfaceLogById(Long id);

    /**
     * 查询接口日志列表
     * 
     * @param gsInterfaceLog 接口日志
     * @return 接口日志集合
     */
    public List<GsInterfaceLog> selectGsInterfaceLogList(GsInterfaceLog gsInterfaceLog);

    /**
     * 新增接口日志
     * 
     * @param gsInterfaceLog 接口日志
     * @return 结果
     */
    public int insertGsInterfaceLog(GsInterfaceLog gsInterfaceLog);

    /**
     * 修改接口日志
     * 
     * @param gsInterfaceLog 接口日志
     * @return 结果
     */
    public int updateGsInterfaceLog(GsInterfaceLog gsInterfaceLog);

    /**
     * 批量删除接口日志
     * 
     * @param ids 需要删除的接口日志ID
     * @return 结果
     */
    public int deleteGsInterfaceLogByIds(Long[] ids);

    /**
     * 删除接口日志信息
     * 
     * @param id 接口日志ID
     * @return 结果
     */
    public int deleteGsInterfaceLogById(Long id);
}
