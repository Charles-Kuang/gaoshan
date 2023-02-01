package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.journal.domain.GsOperationRecord;
import com.ruoyi.journal.service.IGsOperationRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 操作日志Controller
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/journal/gs_operation")
public class GsOperationRecordController extends BaseController
{
    @Autowired
    private IGsOperationRecordService gsOperationRecordService;

    /**
     * 查询操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_operation:list')")
    @GetMapping("/list")
    public TableDataInfo list(GsOperationRecord gsOperationRecord)
    {
        startPage();
        List<GsOperationRecord> list = gsOperationRecordService.selectGsOperationRecordList(gsOperationRecord);
        return getDataTable(list);
    }

    /**
     * 导出操作日志列表
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_operation:export')")
    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsOperationRecord gsOperationRecord)
    {
        List<GsOperationRecord> list = gsOperationRecordService.selectGsOperationRecordList(gsOperationRecord);
        ExcelUtil<GsOperationRecord> util = new ExcelUtil<GsOperationRecord>(GsOperationRecord.class);
        return util.exportExcel(list, "gs_operation");
    }

    /**
     * 获取操作日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_operation:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gsOperationRecordService.selectGsOperationRecordById(id));
    }

    /**
     * 新增操作日志
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_operation:add')")
    @Log(title = "操作日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsOperationRecord gsOperationRecord)
    {
        return toAjax(gsOperationRecordService.insertGsOperationRecord(gsOperationRecord));
    }

    /**
     * 修改操作日志
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_operation:edit')")
    @Log(title = "操作日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsOperationRecord gsOperationRecord)
    {
        return toAjax(gsOperationRecordService.updateGsOperationRecord(gsOperationRecord));
    }

    /**
     * 删除操作日志
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_operation:remove')")
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gsOperationRecordService.deleteGsOperationRecordByIds(ids));
    }
}
