package com.ruoyi.journal.service;

import java.util.List;
import com.ruoyi.journal.domain.GsOperationRecord;

/**
 * 操作日志Service接口
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public interface IGsOperationRecordService 
{
    /**
     * 查询操作日志
     * 
     * @param id 操作日志ID
     * @return 操作日志
     */
    public GsOperationRecord selectGsOperationRecordById(Long id);

    /**
     * 查询操作日志列表
     * 
     * @param gsOperationRecord 操作日志
     * @return 操作日志集合
     */
    public List<GsOperationRecord> selectGsOperationRecordList(GsOperationRecord gsOperationRecord);

    /**
     * 新增操作日志
     * 
     * @param gsOperationRecord 操作日志
     * @return 结果
     */
    public int insertGsOperationRecord(GsOperationRecord gsOperationRecord);

    /**
     * 修改操作日志
     * 
     * @param gsOperationRecord 操作日志
     * @return 结果
     */
    public int updateGsOperationRecord(GsOperationRecord gsOperationRecord);

    /**
     * 批量删除操作日志
     * 
     * @param ids 需要删除的操作日志ID
     * @return 结果
     */
    public int deleteGsOperationRecordByIds(Long[] ids);

    /**
     * 删除操作日志信息
     * 
     * @param id 操作日志ID
     * @return 结果
     */
    public int deleteGsOperationRecordById(Long id);
}
