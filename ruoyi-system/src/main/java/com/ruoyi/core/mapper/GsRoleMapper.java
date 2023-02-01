package com.ruoyi.core.mapper;

import java.util.List;

import com.ruoyi.core.domain.GsRole;
import com.ruoyi.core.domain.GsRoleRegion;
import com.ruoyi.core.domain.GsUserPermission;

/**
 * 前台角色Mapper接口
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public interface GsRoleMapper
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
     * 删除前台角色
     * 
     * @param roleId 前台角色ID
     * @return 结果
     */
    public int deleteGsRoleById(Long roleId);

    /**
     * 批量删除前台角色
     * 
     * @param roleIds 需要删除的数据ID
     * @return 结果
     */
    int deleteGsRoleByIds(Long[] roleIds);

    Long[] selectRoleIdListById(Long userId);

    public int deleteUserRoleByUserId(Long userId);

    /**
     * 批量新增用户角色信息
     *
     * @param userRoleList 用户角色列表
     * @return 结果
     */
    public int batchUserRole(List<GsUserPermission> userRoleList);

    Long[] selectRegionIdListByRoleId(Long roleId);

    int deleteRoleRegionByRoleId(Long roleId);

    int batchRoleRegion(List<GsRoleRegion> list);

//    List<GsRole> selectGsRoleByUserId(Long[] roleIds);
}
