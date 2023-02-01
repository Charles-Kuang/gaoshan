package com.ruoyi.core.service;

import java.util.List;
import com.ruoyi.core.domain.GsRole;

/**
 * 前台角色Service接口
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public interface IGsRoleService 
{
    /**
     * 查询前台角色
     * 
     * @param roleId 前台角色ID
     * @return 前台角色
     */
    public GsRole selectGsRoleById(Long roleId);

    /**
     * 查询前台角色列表
     * 
     * @param gsRole 前台角色
     * @return 前台角色集合
     */
    public List<GsRole> selectGsRoleList(GsRole gsRole);

    /**
     * 新增前台角色
     * 
     * @param gsRole 前台角色
     * @return 结果
     */
    public int insertGsRole(GsRole gsRole);

    /**
     * 修改前台角色
     * 
     * @param gsRole 前台角色
     * @return 结果
     */
    public int updateGsRole(GsRole gsRole);

    /**
     * 批量删除前台角色
     * 
     * @param roleIds 需要删除的前台角色ID
     * @return 结果
     */
    public int deleteGsRoleByIds(Long[] roleIds);

    /**
     * 删除前台角色信息
     * 
     * @param roleId 前台角色ID
     * @return 结果
     */
    public int deleteGsRoleById(Long roleId);
}
