package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.applet.domain.GsHouse;
import com.ruoyi.applet.service.IGsHouseService;
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
 * 实有房屋Controller
 * 
 * @author ruoyi
 * @date 2022-11-26
 */
@RestController
@RequestMapping("/applet/house")
public class GsHouseController extends BaseController
{
    @Autowired
    private IGsHouseService gsHouseService;

    /**
     * 查询实有房屋列表
     */
    @PreAuthorize("@ss.hasPermi('applet:house:list')")
    @GetMapping("/list")
    public TableDataInfo list(GsHouse gsHouse)
    {
        startPage();
        List<GsHouse> list = gsHouseService.selectGsHouseList(gsHouse);
        return getDataTable(list);
    }

    /**
     * 导出实有房屋列表
     */
    @PreAuthorize("@ss.hasPermi('applet:house:export')")
    @Log(title = "实有房屋", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsHouse gsHouse)
    {
        List<GsHouse> list = gsHouseService.selectGsHouseList(gsHouse);
        ExcelUtil<GsHouse> util = new ExcelUtil<GsHouse>(GsHouse.class);
        return util.exportExcel(list, "house");
    }

    /**
     * 获取实有房屋详细信息
     */
    @PreAuthorize("@ss.hasPermi('applet:house:query')")
    @GetMapping(value = "/{houseId}")
    public AjaxResult getInfo(@PathVariable("houseId") Long houseId)
    {
        return AjaxResult.success(gsHouseService.selectGsHouseById(houseId));
    }

    /**
     * 新增实有房屋
     */
    @PreAuthorize("@ss.hasPermi('applet:house:add')")
    @Log(title = "实有房屋", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsHouse gsHouse)
    {
        return toAjax(gsHouseService.insertGsHouse(gsHouse));
    }

    /**
     * 修改实有房屋
     */
    @PreAuthorize("@ss.hasPermi('applet:house:edit')")
    @Log(title = "实有房屋", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsHouse gsHouse)
    {
        return toAjax(gsHouseService.updateGsHouse(gsHouse));
    }

    /**
     * 实有房屋审核
     */
    @Log(title = "实有房屋", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult editOne(@RequestBody GsHouse gsHouse)
    {
        return toAjax(gsHouseService.updateGsHouseOne(gsHouse));
    }

    /**
     * 删除实有房屋
     */
    @PreAuthorize("@ss.hasPermi('applet:house:remove')")
    @Log(title = "实有房屋", businessType = BusinessType.DELETE)
	@DeleteMapping("/{houseIds}")
    public AjaxResult remove(@PathVariable Long[] houseIds)
    {
        return toAjax(gsHouseService.deleteGsHouseByIds(houseIds));
    }
}
