package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.core.domain.GsUserIntegralLog;
import com.ruoyi.core.service.IGsUserIntegralLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 积分记录Controller
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/core/gs_record")
public class GsUserIntegralLogController extends BaseController
{
    @Autowired
    private IGsUserIntegralLogService gsUserIntegralLogService;

    /**
     * 查询积分记录列表
     */
    @PreAuthorize("@ss.hasPermi('core:gs_record:list')")
    @GetMapping("/list")
    public TableDataInfo list(GsUserIntegralLog gsUserIntegralLog)
    {
        startPage();
        List<GsUserIntegralLog> list = gsUserIntegralLogService.selectGsUserIntegralLogList(gsUserIntegralLog);
        return getDataTable(list);
    }

    /**
     * 导出积分记录列表
     */
    @PreAuthorize("@ss.hasPermi('core:gs_record:export')")
    @Log(title = "积分记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsUserIntegralLog gsUserIntegralLog)
    {
        List<GsUserIntegralLog> list = gsUserIntegralLogService.selectGsUserIntegralLogList(gsUserIntegralLog);
        ExcelUtil<GsUserIntegralLog> util = new ExcelUtil<GsUserIntegralLog>(GsUserIntegralLog.class);
        return util.exportExcel(list, "gs_record");
    }

    /**
     * 获取积分记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('core:gs_record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(gsUserIntegralLogService.selectGsUserIntegralLogById(id));
    }

    /**
     * 新增积分记录
     */
    @PreAuthorize("@ss.hasPermi('core:gs_record:add')")
    @Log(title = "积分记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsUserIntegralLog gsUserIntegralLog)
    {
        return toAjax(gsUserIntegralLogService.insertGsUserIntegralLog(gsUserIntegralLog));
    }

    /**
     * 修改积分记录
     */
    @PreAuthorize("@ss.hasPermi('core:gs_record:edit')")
    @Log(title = "积分记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsUserIntegralLog gsUserIntegralLog)
    {
        return toAjax(gsUserIntegralLogService.updateGsUserIntegralLog(gsUserIntegralLog));
    }

    /**
     * 删除积分记录
     */
    @PreAuthorize("@ss.hasPermi('core:gs_record:remove')")
    @Log(title = "积分记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(gsUserIntegralLogService.deleteGsUserIntegralLogByIds(ids));
    }
}
