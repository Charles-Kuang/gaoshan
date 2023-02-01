package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.core.domain.GsStreet;
import com.ruoyi.core.service.IGsStreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 街路巷管理Controller
 * 
 * @author chenghaiqin
 * @date 2022-12-30
 */
@RestController
@RequestMapping("/core/street")
public class GsStreetController extends BaseController
{
    @Autowired
    private IGsStreetService gsStreetService;

    /**
     * 查询街路巷管理列表
     */
    @PreAuthorize("@ss.hasPermi('core:street:list')")
    @GetMapping("/list")
    public AjaxResult list(GsStreet gsStreet)
    {
        List<GsStreet> list = gsStreetService.selectGsStreetList(gsStreet);
        return AjaxResult.success(list);
    }

    /**
     * 导出街路巷管理列表
     */
    @PreAuthorize("@ss.hasPermi('core:street:export')")
    @Log(title = "街路巷管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsStreet gsStreet)
    {
        List<GsStreet> list = gsStreetService.selectGsStreetList(gsStreet);
        ExcelUtil<GsStreet> util = new ExcelUtil<GsStreet>(GsStreet.class);
        return util.exportExcel(list, "street");
    }

    /**
     * 获取街路巷管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('core:street:query')")
    @GetMapping(value = "/{streetId}")
    public AjaxResult getInfo(@PathVariable("streetId") Long streetId)
    {
        return AjaxResult.success(gsStreetService.selectGsStreetById(streetId));
    }

    /**
     * 新增街路巷管理
     */
    @PreAuthorize("@ss.hasPermi('core:street:add')")
    @Log(title = "街路巷管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsStreet gsStreet)
    {
        return toAjax(gsStreetService.insertGsStreet(gsStreet));
    }

    /**
     * 修改街路巷管理
     */
    @PreAuthorize("@ss.hasPermi('core:street:edit')")
    @Log(title = "街路巷管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsStreet gsStreet)
    {
        return toAjax(gsStreetService.updateGsStreet(gsStreet));
    }

    /**
     * 修改实有人口登记
     */
    @Log(title = "街路巷管理", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult auditOne(@RequestBody GsStreet gsStreet) {
        return toAjax(gsStreetService.updateGsStreetOne(gsStreet));
    }
    /**
     * 删除街路巷管理
     */
    @PreAuthorize("@ss.hasPermi('core:street:remove')")
    @Log(title = "街路巷管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{streetIds}")
    public AjaxResult remove(@PathVariable Long[] streetIds)
    {
        return toAjax(gsStreetService.deleteGsStreetByIds(streetIds));
    }
}
