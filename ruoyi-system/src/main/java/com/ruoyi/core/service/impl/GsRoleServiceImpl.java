package com.ruoyi.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.applet.mapper.GsRegionMapper;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.core.domain.GsRoleRegion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.core.mapper.GsRoleMapper;
import com.ruoyi.core.domain.GsRole;
import com.ruoyi.core.service.IGsRoleService;

/**
 * 前台角色Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
@Service
public class GsRoleServiceImpl implements IGsRoleService 
{
    @Autowired
    GsRoleMapper gsRoleMapper;

    @Autowired
    GsRegionMapper gsRegionMapper;

    /**
     * 查询前台角色
     * 
     * @param roleId 前台角色ID
     * @return 前台角色
     */
    @Override
    public GsRole selectGsRoleById(Long roleId)
    {
        GsRole gsRole = gsRoleMapper.selectGsRoleById(roleId);
        if(gsRole.getDataScope().equals("5")){
            Long[] codes = gsRoleMapper.selectRegionIdListByRoleId(roleId);
            if(codes.length>0){
                gsRole.setRegionIds(codes);
                gsRole.setChildren(gsRegionMapper.selectRegionListByRegionIds(codes));
            }
        }
        return gsRole;
    }

    /**
     * 查询前台角色列表
     * 
     * @param gsRole 前台角色
     * @return 前台角色
     */
    @Override
    public List<GsRole> selectGsRoleList(GsRole gsRole)
    {
        return gsRoleMapper.selectGsRoleList(gsRole);
    }

    /**
     * 新增前台角色
     * 
     * @param gsRole 前台角色
     * @return 结果
     */
    @Override
    public int insertGsRole(GsRole gsRole)
    {
        gsRole.setCreateTime(DateUtils.getNowDate());
        insertRoleRegion(gsRole);
        return gsRoleMapper.insertGsRole(gsRole);
    }

    /**
     * 修改前台角色
     * 
     * @param gsRole 前台角色
     * @return 结果
     */
    @Override
    public int updateGsRole(GsRole gsRole)
    {
        gsRole.setUpdateTime(DateUtils.getNowDate());
        // 删除用户与角色关联
        int i = gsRoleMapper.deleteRoleRegionByRoleId(gsRole.getRoleId());
        // 新增用户与角色管理
        insertRoleRegion(gsRole);
        return gsRoleMapper.updateGsRole(gsRole);
    }

    /**
     * 新增用户角色信息
     *
     * @param gsRole 用户对象
     */
    public void insertRoleRegion(GsRole gsRole)
    {
        Long[] codes = gsRole.getRegionIds();
        if (StringUtils.isNotNull(codes))
        {
            // 新增用户与角色管理
            List<GsRoleRegion> list = new ArrayList<GsRoleRegion>();
            for (Long code : codes)
            {
                GsRoleRegion ur = new GsRoleRegion();
                ur.setActorId(gsRole.getRoleId());
                ur.setRegionId(code);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                gsRoleMapper.batchRoleRegion(list);
            }
        }
    }

    /**
     * 批量删除前台角色
     * 
     * @param roleIds 需要删除的前台角色ID
     * @return 结果
     */
    @Override
    public int deleteGsRoleByIds(Long[] roleIds)
    {
        return gsRoleMapper.deleteGsRoleByIds(roleIds);
    }

    /**
     * 删除前台角色信息
     * 
     * @param roleId 前台角色ID
     * @return 结果
     */
    @Override
    public int deleteGsRoleById(Long roleId)
    {
        return gsRoleMapper.deleteGsRoleById(roleId);
    }
}
