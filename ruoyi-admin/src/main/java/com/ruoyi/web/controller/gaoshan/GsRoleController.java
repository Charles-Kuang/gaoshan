package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.core.domain.GsRole;
import com.ruoyi.core.service.IGsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前台角色Controller
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/core/gs_role")
public class GsRoleController extends BaseController
{
    @Autowired
    private IGsRoleService gsRoleService;

    /**
     * 查询前台角色列表
     */
    @PreAuthorize("@ss.hasPermi('core:gs_role:list')")
    @GetMapping("/list")
    public TableDataInfo list(GsRole gsRole)
    {
        startPage();
        List<GsRole> list = gsRoleService.selectGsRoleList(gsRole);
        return getDataTable(list);
    }

    /**
     * 导出前台角色列表
     */
    @PreAuthorize("@ss.hasPermi('core:gs_role:export')")
    @Log(title = "前台角色", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsRole gsRole)
    {
        List<GsRole> list = gsRoleService.selectGsRoleList(gsRole);
        ExcelUtil<GsRole> util = new ExcelUtil<GsRole>(GsRole.class);
        return util.exportExcel(list, "gs_role");
    }

    /**
     * 获取前台角色详细信息
     */
    @PreAuthorize("@ss.hasPermi('core:gs_role:query')")
    @GetMapping(value = "/{roleId}")
    public AjaxResult getInfo(@PathVariable("roleId") Long roleId)
    {
        return AjaxResult.success(gsRoleService.selectGsRoleById(roleId));
    }

    /**
     * 新增前台角色
     */
    @PreAuthorize("@ss.hasPermi('core:gs_role:add')")
    @Log(title = "前台角色", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsRole gsRole)
    {
        return toAjax(gsRoleService.insertGsRole(gsRole));
    }

    /**
     * 修改前台角色
     */
    @PreAuthorize("@ss.hasPermi('core:gs_role:edit')")
    @Log(title = "前台角色", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsRole gsRole)
    {
        return toAjax(gsRoleService.updateGsRole(gsRole));
    }

    /**
     * 删除前台角色
     */
    @PreAuthorize("@ss.hasPermi('core:gs_role:remove')")
    @Log(title = "前台角色", businessType = BusinessType.DELETE)
	@DeleteMapping("/{roleIds}")
    public AjaxResult remove(@PathVariable Long[] roleIds)
    {
        return toAjax(gsRoleService.deleteGsRoleByIds(roleIds));
    }
}
