package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.applet.domain.GsPerson;
import com.ruoyi.applet.service.IGsPersonService;
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
 * 实有人口登记Controller
 *
 * @author ruoyi
 * @date 2022-11-26
 */
@RestController
@RequestMapping("/applet/person")
public class GsPersonController extends BaseController {
    @Autowired
    private IGsPersonService gsPersonService;

    /**
     * 查询实有人口登记列表
     */
    @PreAuthorize("@ss.hasPermi('applet:person:list')")
    @GetMapping("/list")
    public TableDataInfo list(GsPerson gsPerson) {
        startPage();
        List<GsPerson> list = gsPersonService.selectGsPersonList(gsPerson);
        return getDataTable(list);
    }

    /**
     * 导出实有人口登记列表
     */
    @PreAuthorize("@ss.hasPermi('applet:person:export')")
    @Log(title = "实有人口登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsPerson gsPerson) {
        List<GsPerson> list = gsPersonService.selectGsPersonList(gsPerson);
        ExcelUtil<GsPerson> util = new ExcelUtil<GsPerson>(GsPerson.class);
        return util.exportExcel(list, "person");
    }

    /**
     * 获取实有人口登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('applet:person:query')")
    @GetMapping(value = "/{personId}")
    public AjaxResult getInfo(@PathVariable("personId") Long personId) {
        return AjaxResult.success(gsPersonService.selectGsPersonById(personId));
    }

    /**
     * 新增实有人口登记
     */
    @PreAuthorize("@ss.hasPermi('applet:person:add')")
    @Log(title = "实有人口登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsPerson gsPerson) {
        return toAjax(gsPersonService.insertGsPerson(gsPerson));
    }

    /**
     * 修改实有人口登记
     */
    @PreAuthorize("@ss.hasPermi('applet:person:edit')")
    @Log(title = "实有人口登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsPerson gsPerson) {
        return toAjax(gsPersonService.updateGsPerson(gsPerson));
    }

    /**
     * 修改实有人口登记
     */
    @Log(title = "实有人口登记", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult editOne(@RequestBody GsPerson gsPerson) {
        return toAjax(gsPersonService.updateGsPersonOne(gsPerson));
    }

    /**
     * 删除实有人口登记
     */
    @PreAuthorize("@ss.hasPermi('applet:person:remove')")
    @Log(title = "实有人口登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{personIds}")
    public AjaxResult remove(@PathVariable Long[] personIds) {
        return toAjax(gsPersonService.deleteGsPersonByIds(personIds));
    }
}
