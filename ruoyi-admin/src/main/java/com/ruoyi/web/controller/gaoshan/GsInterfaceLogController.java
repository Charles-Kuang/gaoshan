package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.journal.domain.GsInterfaceLog;
import com.ruoyi.journal.service.IGsInterfaceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 接口日志Controller
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/journal/gs_interface")
public class GsInterfaceLogController extends BaseController
{
    @Autowired
    private IGsInterfaceLogService gsInterfaceLogService;

    /**
     * 查询接口日志列表
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_interface:list')")
    @GetMapping("/list")
    public TableDataInfo list(GsInterfaceLog gsInterfaceLog)
    {
        startPage();
        List<GsInterfaceLog> list = gsInterfaceLogService.selectGsInterfaceLogList(gsInterfaceLog);
        return getDataTable(list);
    }

    /**
     * 导出接口日志列表
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_interface:export')")
    @Log(title = "接口日志", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsInterfaceLog gsInterfaceLog)
    {
        List<GsInterfaceLog> list = gsInterfaceLogService.selectGsInterfaceLogList(gsInterfaceLog);
        ExcelUtil<GsInterfaceLog> util = new ExcelUtil<GsInterfaceLog>(GsInterfaceLog.class);
        return util.exportExcel(list, "gs_interface");
    }

    /**
     * 获取接口日志详细信息
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_interface:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gsInterfaceLogService.selectGsInterfaceLogById(id));
    }

    /**
     * 新增接口日志
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_interface:add')")
    @Log(title = "接口日志", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsInterfaceLog gsInterfaceLog)
    {
        return toAjax(gsInterfaceLogService.insertGsInterfaceLog(gsInterfaceLog));
    }

    /**
     * 修改接口日志
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_interface:edit')")
    @Log(title = "接口日志", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsInterfaceLog gsInterfaceLog)
    {
        return toAjax(gsInterfaceLogService.updateGsInterfaceLog(gsInterfaceLog));
    }

    /**
     * 删除接口日志
     */
    @PreAuthorize("@ss.hasPermi('journal:gs_interface:remove')")
    @Log(title = "接口日志", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gsInterfaceLogService.deleteGsInterfaceLogByIds(ids));
    }
}
