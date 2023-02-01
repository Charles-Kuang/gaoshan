package com.ruoyi.web.controller.gaoshan;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.core.domain.GsUser;
import com.ruoyi.core.service.IGsUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小程序用户Controller
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@RestController
@RequestMapping("/core/gs_user")
public class GsUserController extends BaseController
{
    @Autowired
    private IGsUserService gsUserService;

    /**
     * 查询小程序用户列表
     */
    @PreAuthorize("@ss.hasPermi('core:gs_user:list')")
    @GetMapping("/list")
    public TableDataInfo list(GsUser gsUser)
    {
        startPage();
        List<GsUser> list = gsUserService.selectGsUserList(gsUser);
        return getDataTable(list);
    }

    /**
     * 导出小程序用户列表
     */
    @PreAuthorize("@ss.hasPermi('core:gs_user:export')")
    @Log(title = "小程序用户", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(GsUser gsUser)
    {
        List<GsUser> list = gsUserService.selectGsUserList(gsUser);
        ExcelUtil<GsUser> util = new ExcelUtil<GsUser>(GsUser.class);
        return util.exportExcel(list, "gs_user");
    }

    /**
     * 获取小程序用户详细信息
     */
    @PreAuthorize("@ss.hasPermi('core:gs_user:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return AjaxResult.success(gsUserService.selectGsUserById(userId));
    }

    /**
     * 新增小程序用户
     */
    @PreAuthorize("@ss.hasPermi('core:gs_user:add')")
    @Log(title = "小程序用户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GsUser gsUser)
    {
        return toAjax(gsUserService.insertGsUser(gsUser));
    }

    /**
     * 修改小程序用户
     */
    @PreAuthorize("@ss.hasPermi('core:gs_user:edit')")
    @Log(title = "小程序用户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GsUser gsUser)
    {
        return toAjax(gsUserService.updateGsUser(gsUser));
    }

    /**
     * 删除小程序用户
     */
    @PreAuthorize("@ss.hasPermi('core:gs_user:remove')")
    @Log(title = "小程序用户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(gsUserService.deleteGsUserByIds(userIds));
    }
}
