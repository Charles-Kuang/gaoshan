package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.applet.domain.GsWxConsult;
import com.ruoyi.applet.service.IGsWxConsultService;
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
 * 服务咨询Controller
 * 
 * @author ruoyi
 * @date 2022-11-18
 */
@RestController
@RequestMapping("/applet/consult")
public class GsWxConsultController extends BaseController
{
    @Autowired
    private IGsWxConsultService gsWxConsultService;

    /**
     * 查询服务咨询列表
     */
    @PreAuthorize("@ss.hasPermi('applet:consult:list')")
    @GetMapping("/list")
    public TableDataInfo list(GsWxConsult gsWxConsult)
    {
        startPage();
        List<GsWxConsult> list = gsWxConsultService.selectGsWxConsultList(gsWxConsult);
        return getDataTable(list);
    }

    /**
     * 导出服务咨询列表
     */
    @PreAuthorize("@ss.hasPermi('applet:consult:export')")
    @Log(title = "服务咨询", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsWxConsult gsWxConsult)
    {
        List<GsWxConsult> list = gsWxConsultService.selectGsWxConsultList(gsWxConsult);
        ExcelUtil<GsWxConsult> util = new ExcelUtil<GsWxConsult>(GsWxConsult.class);
        return util.exportExcel(list, "consult");
    }

    /**
     * 获取服务咨询详细信息
     */
    @PreAuthorize("@ss.hasPermi('applet:consult:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(gsWxConsultService.selectGsWxConsultById(id));
    }

    /**
     * 新增服务咨询
     */
    @PreAuthorize("@ss.hasPermi('applet:consult:add')")
    @Log(title = "服务咨询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsWxConsult gsWxConsult)
    {
        return toAjax(gsWxConsultService.insertGsWxConsult(gsWxConsult));
    }

    /**
     * 修改服务咨询
     */
    @PreAuthorize("@ss.hasPermi('applet:consult:edit')")
    @Log(title = "服务咨询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsWxConsult gsWxConsult)
    {
        return toAjax(gsWxConsultService.updateGsWxConsult(gsWxConsult));
    }

    /**
     * 删除服务咨询
     */
    @PreAuthorize("@ss.hasPermi('applet:consult:remove')")
    @Log(title = "服务咨询", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(gsWxConsultService.deleteGsWxConsultByIds(ids));
    }
}
