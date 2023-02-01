package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.applet.service.IChinaService;
import com.ruoyi.applet.service.IGsRegionService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.GsRegion;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import liquibase.pro.packaged.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 行政区Controller
 *
 * @author ruoyi
 * @date 2022-11-03
 */
@RestController
@RequestMapping("/system/region")
public class GsRegionController extends BaseController {
    @Autowired
    private IGsRegionService gsRegionService;

    @Autowired
    private IChinaService chinaService;
    
    /**
     * 查询行政区列表
     */
    @PreAuthorize("@ss.hasPermi('applet:region:list')")
    @GetMapping("/list")
    public AjaxResult list(GsRegion gsRegion) {
        List<GsRegion> list = gsRegionService.selectGsRegionList(gsRegion);
        return AjaxResult.success(list);
    }

    /**
     * 四级联查行政区列表
     */
    @GetMapping("/listOne")
    public AjaxResult listOne(GsRegion gsRegion) {
        List<GsRegion> list = gsRegionService.selectGsRegionListOne(gsRegion);
        return AjaxResult.success(list);
    }

    /**
     * 行政区列表
     */
    @GetMapping("/listTwo")
    public AjaxResult listTwo(GsRegion gsRegion) {
        List<GsRegion> list = gsRegionService.selectGsRegionListTwo(gsRegion);
        return AjaxResult.success(list);
    }

    /**
     * 导出行政区列表
     */
    @PreAuthorize("@ss.hasPermi('applet:region:export')")
    @Log(title = "行政区", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsRegion gsRegion) {
        List<GsRegion> list = gsRegionService.selectGsRegionList(gsRegion);
        ExcelUtil<GsRegion> util = new ExcelUtil<GsRegion>(GsRegion.class);
        return util.exportExcel(list, "region");
    }

    /**
     * 获取行政区详细信息
     */
    @PreAuthorize("@ss.hasPermi('applet:region:query')")
    @GetMapping(value = "/{code}")
    public AjaxResult getInfo(@PathVariable("code") Long code) {
        return AjaxResult.success(gsRegionService.selectGsRegionByCode(code));
    }

    /**
     * 根据code查询子行政区
     */
    @GetMapping(value = "/getChild/{code}")
    public AjaxResult getChild(@PathVariable("code") Long code) {
        return AjaxResult.success(gsRegionService.getChild(code));
    }

    /**
     * 新增行政区
     */
    @PreAuthorize("@ss.hasPermi('applet:region:add')")
    @Log(title = "行政区", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsRegion gsRegion) {
        if (gsRegion.getCode() == null) return AjaxResult.error("行政区代码不能为空");
        if (gsRegionService.selectGsRegionByCode(gsRegion.getCode()) != null) return AjaxResult.error("该行政区已存在");
        return toAjax(gsRegionService.insertGsRegion(gsRegion));
    }

    /**
     * 新增行政区
     */
    @GetMapping("/{name}/{code}/{parentCode}/{type}")
    public AjaxResult add(@PathVariable("name") String name,@PathVariable("code") Long code,@PathVariable("parentCode") Long parentCode,@PathVariable("type") String type){
        GsRegion gsRegion=new GsRegion();
        gsRegion.setParentCode(parentCode);
        gsRegion.setName(name);
        gsRegion.setCode(code);
        gsRegion.setType(type);
        return toAjax(gsRegionService.insertGsRegion(gsRegion));
    }

    /**
     * 新增行政区
     */
    @GetMapping("/{name}/{code}/{parentCode}")
    public AjaxResult add(@PathVariable("name") String name,@PathVariable("code") Long code,@PathVariable("parentCode") Long parentCode){
        GsRegion gsRegion=new GsRegion();
        gsRegion.setParentCode(parentCode);
        gsRegion.setName(name);
        gsRegion.setCode(code);
        return toAjax(gsRegionService.insertGsRegion(gsRegion));
    }

    /**
     * 修改行政区
     */
    @PreAuthorize("@ss.hasPermi('applet:region:edit')")
    @Log(title = "行政区", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsRegion gsRegion) {
        return toAjax(gsRegionService.updateGsRegion(gsRegion));
    }

    /**
     * 修改行政区
     */
    @PreAuthorize("@ss.hasPermi('applet:region:edit')")
    @Log(title = "行政区", businessType = BusinessType.UPDATE)
    @PutMapping("/edit")
    public AjaxResult editOne(@RequestBody GsRegion gsRegion) {
        return toAjax(gsRegionService.updateGsRegionOne(gsRegion));
    }

    /**
     * 删除行政区
     */
    @PreAuthorize("@ss.hasPermi('applet:region:remove')")
    @Log(title = "行政区", businessType = BusinessType.DELETE)
    @DeleteMapping("/{regionIds}")
    public AjaxResult remove(@PathVariable Long[] regionIds) {
        return toAjax(gsRegionService.deleteGsRegionByIds(regionIds));
    }

    /**
     * 拉取各地区代码子
     */
    @GetMapping("/getChildRegion/{code}")
    public AjaxResult getChildRegion(@PathVariable("code") Long code) {
        GsRegion gsRegion = gsRegionService.selectGsRegionByCode(code);
        chinaService.test(gsRegion);
        return AjaxResult.success("插入成功");
    }

    /**
     * 获取区域树列表
     */
    @GetMapping("/cityTree")
    public AjaxResult cityTree()
    {
        return AjaxResult.success(gsRegionService.selectRegionList());
    }

    /**
     * 获取县区列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/city/list")
    public TableDataInfo cityList(GsRegion region)
    {
        startPage();
        List<GsRegion> list = gsRegionService.selectGsRegionList(region);
        return getDataTable(list);
    }

    /**
     * 根据code查询子行政区
     */
    @GetMapping(value = "/getChildOne/{code}")
    public AjaxResult getChildOne(@PathVariable("code") Long code) {
        return AjaxResult.success(gsRegionService.getChildOne(code));
    }

    /**
     * 根据code查询子行政区
     */
    @GetMapping(value = "/listGsRegion")
    public AjaxResult listGsRegion(GsRegion region) {
        return AjaxResult.success(gsRegionService.listGsRegion(region));
    }

    /**
     * 状态修改
     */
    @PutMapping("/changeInvalidFlag")
    public AjaxResult changeStatus(@RequestBody GsRegion region)
    {
        return toAjax(gsRegionService.updateInvalidFlag(region));
    }
}
