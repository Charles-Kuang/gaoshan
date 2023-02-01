package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.journal.domain.GsErrorLog;
import com.ruoyi.journal.service.IGsErrorLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 错误日志Controller
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/journal/gs_error")
public class GsErrorLogController extends BaseController
{
    @Autowired
    private IGsErrorLogService gsErrorLogService;

    /**
     * 查询错误日志列表
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_error:list')")
    @GetMapping("/list")
    public TableDataInfo list(GsErrorLog gsErrorLog)
    {
        startPage();
        List<GsErrorLog> list = gsErrorLogService.selectGsErrorLogList(gsErrorLog);
        return getDataTable(list);
    }

    /**
     * 导出错误日志列表
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_error:export')")
    @Log(title = "错误日志", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsErrorLog gsErrorLog)
    {
        List<GsErrorLog> list = gsErrorLogService.selectGsErrorLogList(gsErrorLog);
        ExcelUtil<GsErrorLog> util = new ExcelUtil<GsErrorLog>(GsErrorLog.class);
        return util.exportExcel(list, "gs_error");
    }

    /**
     * 获取错误日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_error:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gsErrorLogService.selectGsErrorLogById(id));
    }

    /**
     * 新增错误日志
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_error:add')")
    @Log(title = "错误日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsErrorLog gsErrorLog)
    {
        return toAjax(gsErrorLogService.insertGsErrorLog(gsErrorLog));
    }

    /**
     * 修改错误日志
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_error:edit')")
    @Log(title = "错误日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsErrorLog gsErrorLog)
    {
        return toAjax(gsErrorLogService.updateGsErrorLog(gsErrorLog));
    }

    /**
     * 删除错误日志
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_error:remove')")
    @Log(title = "错误日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gsErrorLogService.deleteGsErrorLogByIds(ids));
    }
}
