package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.applet.domain.GsUnit;
import com.ruoyi.applet.service.IGsUnitService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实有单位列Controller
 * 
 * @author ruoyi
 * @date 2022-11-26
 */
@RestController
@RequestMapping("/applet/unit")
public class GsUnitController extends BaseController
{
    @Autowired
    private IGsUnitService gsUnitService;

    /**
     * 查询实有单位列列表
     */
    @PreAuthorize("@ss.hasPermi('applet:unit:list')")
    @GetMapping("/list")
    public TableDataInfo list(GsUnit gsUnit)
    {
        startPage();
        List<GsUnit> list = gsUnitService.selectGsUnitList(gsUnit);
        return getDataTable(list);
    }

    /**
     * 导出实有单位列列表
     */
    @PreAuthorize("@ss.hasPermi('applet:unit:export')")
    @Log(title = "实有单位列", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsUnit gsUnit)
    {
        List<GsUnit> list = gsUnitService.selectGsUnitList(gsUnit);
        ExcelUtil<GsUnit> util = new ExcelUtil<GsUnit>(GsUnit.class);
        return util.exportExcel(list, "unit");
    }

    /**
     * 获取实有单位列详细信息
     */
    @PreAuthorize("@ss.hasPermi('applet:unit:query')")
    @GetMapping(value = "/{unitId}")
    public AjaxResult getInfo(@PathVariable("unitId") Long unitId)
    {
        return AjaxResult.success(gsUnitService.selectGsUnitById(unitId));
    }

    /**
     * 新增实有单位列
     */
    @PreAuthorize("@ss.hasPermi('applet:unit:add')")
    @Log(title = "实有单位列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsUnit gsUnit)
    {
        return toAjax(gsUnitService.insertGsUnit(gsUnit));
    }

    /**
     * 修改实有单位列
     */
    @PreAuthorize("@ss.hasPermi('applet:unit:edit')")
    @Log(title = "实有单位列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsUnit gsUnit)
    {
        return toAjax(gsUnitService.updateGsUnit(gsUnit));
    }

    /**
     * 修改实有单位列
     */
    @PreAuthorize("@ss.hasPermi('applet:unit:edit')")
    @Log(title = "实有单位列", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult editOne(@RequestBody GsUnit gsUnit)
    {
        return toAjax(gsUnitService.updateGsUnitOne(gsUnit));
    }

    /**
     * 删除实有单位列
     */
    @PreAuthorize("@ss.hasPermi('applet:unit:remove')")
    @Log(title = "实有单位列", businessType = BusinessType.DELETE)
	@DeleteMapping("/{unitIds}")
    public AjaxResult remove(@PathVariable String[] unitIds)
    {
        return toAjax(gsUnitService.deleteGsUnitByIds(unitIds));
    }
}
