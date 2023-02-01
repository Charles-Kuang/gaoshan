package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.applet.domain.GsRegionDetail;
import com.ruoyi.applet.service.IGsRegionDetailService;
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
 * 标准详细地址Controller
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/applet/address")
public class GsRegionDetailController extends BaseController
{
    @Autowired
    private IGsRegionDetailService gsRegionDetailService;

    /**
     * 查询标准详细地址列表
     */
    @PreAuthorize("@ss.hasPermi('applet:address:list')")
    @GetMapping("/list")
    public TableDataInfo list(GsRegionDetail gsRegionDetail)
    {
        startPage();
        List<GsRegionDetail> list = gsRegionDetailService.selectGsRegionDetailList(gsRegionDetail);
        return getDataTable(list);
    }

    /**
     * 导出标准详细地址列表
     */
    @PreAuthorize("@ss.hasPermi('applet:address:export')")
    @Log(title = "标准详细地址", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsRegionDetail gsRegionDetail)
    {
        List<GsRegionDetail> list = gsRegionDetailService.selectGsRegionDetailList(gsRegionDetail);
        ExcelUtil<GsRegionDetail> util = new ExcelUtil<GsRegionDetail>(GsRegionDetail.class);
        return util.exportExcel(list, "address");
    }

    /**
     * 获取标准详细地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('applet:address:query')")
    @GetMapping(value = "/{addressId}")
    public AjaxResult getInfo(@PathVariable("addressId") Long addressId)
    {
        return AjaxResult.success(gsRegionDetailService.selectGsRegionDetailById(addressId));
    }

    /**
     * 新增标准详细地址
     */
    @PreAuthorize("@ss.hasPermi('applet:address:add')")
    @Log(title = "标准详细地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsRegionDetail gsRegionDetail)
    {
        return toAjax(gsRegionDetailService.insertGsRegionDetail(gsRegionDetail));
    }

    /**
     * 修改标准详细地址
     */
    @PreAuthorize("@ss.hasPermi('applet:address:edit')")
    @Log(title = "标准详细地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsRegionDetail gsRegionDetail)
    {
        return toAjax(gsRegionDetailService.updateGsRegionDetail(gsRegionDetail));
    }

    /**
     * 删除标准详细地址
     */
    @PreAuthorize("@ss.hasPermi('applet:address:remove')")
    @Log(title = "标准详细地址", businessType = BusinessType.DELETE)
	@DeleteMapping("/{addressIds}")
    public AjaxResult remove(@PathVariable Long[] addressIds)
    {
        return toAjax(gsRegionDetailService.deleteGsRegionDetailByIds(addressIds));
    }
}
