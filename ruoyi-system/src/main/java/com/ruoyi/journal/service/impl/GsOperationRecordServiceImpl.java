package com.ruoyi.journal.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.journal.mapper.GsOperationRecordMapper;
import com.ruoyi.journal.domain.GsOperationRecord;
import com.ruoyi.journal.service.IGsOperationRecordService;

/**
 * 操作日志Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@Service
public class GsOperationRecordServiceImpl implements IGsOperationRecordService 
{
    @Autowired
    private GsOperationRecordMapper gsOperationRecordMapper;

    /**
     * 查询操作日志
     * 
     * @param id 操作日志ID
     * @return 操作日志
     */
    @Override
    public GsOperationRecord selectGsOperationRecordById(Long id)
    {
        return gsOperationRecordMapper.selectGsOperationRecordById(id);
    }

    /**
     * 查询操作日志列表
     * 
     * @param gsOperationRecord 操作日志
     * @return 操作日志
     */
    @Override
    public List<GsOperationRecord> selectGsOperationRecordList(GsOperationRecord gsOperationRecord)
    {
        return gsOperationRecordMapper.selectGsOperationRecordList(gsOperationRecord);
    }

    /**
     * 新增操作日志
     * 
     * @param gsOperationRecord 操作日志
     * @return 结果
     */
    @Override
    public int insertGsOperationRecord(GsOperationRecord gsOperationRecord)
    {
        return gsOperationRecordMapper.insertGsOperationRecord(gsOperationRecord);
    }

    /**
     * 修改操作日志
     * 
     * @param gsOperationRecord 操作日志
     * @return 结果
     */
    @Override
    public int updateGsOperationRecord(GsOperationRecord gsOperationRecord)
    {
        return gsOperationRecordMapper.updateGsOperationRecord(gsOperationRecord);
    }

    /**
     * 批量删除操作日志
     * 
     * @param ids 需要删除的操作日志ID
     * @return 结果
     */
    @Override
    public int deleteGsOperationRecordByIds(Long[] ids)
    {
        return gsOperationRecordMapper.deleteGsOperationRecordByIds(ids);
    }

    /**
     * 删除操作日志信息
     * 
     * @param id 操作日志ID
     * @return 结果
     */
    @Override
    public int deleteGsOperationRecordById(Long id)
    {
        return gsOperationRecordMapper.deleteGsOperationRecordById(id);
    }
}
