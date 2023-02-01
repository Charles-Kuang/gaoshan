package com.ruoyi.journal.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.journal.mapper.GsInterfaceLogMapper;
import com.ruoyi.journal.domain.GsInterfaceLog;
import com.ruoyi.journal.service.IGsInterfaceLogService;

/**
 * 接口日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@Service
public class GsInterfaceLogServiceImpl implements IGsInterfaceLogService 
{
    @Autowired
    private GsInterfaceLogMapper gsInterfaceLogMapper;

    /**
     * 查询接口日志
     * 
     * @param id 接口日志ID
     * @return 接口日志
     */
    @Override
    public GsInterfaceLog selectGsInterfaceLogById(Long id)
    {
        return gsInterfaceLogMapper.selectGsInterfaceLogById(id);
    }

    /**
     * 查询接口日志列表
     * 
     * @param gsInterfaceLog 接口日志
     * @return 接口日志
     */
    @Override
    public List<GsInterfaceLog> selectGsInterfaceLogList(GsInterfaceLog gsInterfaceLog)
    {
        return gsInterfaceLogMapper.selectGsInterfaceLogList(gsInterfaceLog);
    }

    /**
     * 新增接口日志
     * 
     * @param gsInterfaceLog 接口日志
     * @return 结果
     */
    @Override
    public int insertGsInterfaceLog(GsInterfaceLog gsInterfaceLog)
    {
        gsInterfaceLog.setCreateTime(DateUtils.getNowDate());
        return gsInterfaceLogMapper.insertGsInterfaceLog(gsInterfaceLog);
    }

    /**
     * 修改接口日志
     * 
     * @param gsInterfaceLog 接口日志
     * @return 结果
     */
    @Override
    public int updateGsInterfaceLog(GsInterfaceLog gsInterfaceLog)
    {
        return gsInterfaceLogMapper.updateGsInterfaceLog(gsInterfaceLog);
    }

    /**
     * 批量删除接口日志
     * 
     * @param ids 需要删除的接口日志ID
     * @return 结果
     */
    @Override
    public int deleteGsInterfaceLogByIds(Long[] ids)
    {
        return gsInterfaceLogMapper.deleteGsInterfaceLogByIds(ids);
    }

    /**
     * 删除接口日志信息
     * 
     * @param id 接口日志ID
     * @return 结果
     */
    @Override
    public int deleteGsInterfaceLogById(Long id)
    {
        return gsInterfaceLogMapper.deleteGsInterfaceLogById(id);
    }
}
