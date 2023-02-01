package com.ruoyi.core.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.core.domain.GsUserPermission;
import com.ruoyi.core.mapper.GsRoleMapper;
import com.ruoyi.system.domain.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.core.domain.GsUserIntegralLog;
import com.ruoyi.core.mapper.GsUserMapper;
import com.ruoyi.core.domain.GsUser;
import com.ruoyi.core.service.IGsUserService;

/**
 * 小程序用户Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-12-02
 */
@Service
public class GsUserServiceImpl implements IGsUserService 
{
    @Autowired
    private GsUserMapper gsUserMapper;

    @Autowired
    private GsRoleMapper gsRoleMapper;

    /**
     * 查询小程序用户
     * 
     * @param userId 小程序用户ID
     * @return 小程序用户
     */
    @Override
    public GsUser selectGsUserById(Long userId)
    {
        GsUser gsUser = gsUserMapper.selectGsUserById(userId);
        Long[] longs = gsRoleMapper.selectRoleIdListById(userId);
        if(longs!=null)gsUser.setRoleIds(longs);
        gsUser.setRoles(gsRoleMapper.selectGsRoleList(null));
//        if(SysUser.isAdmin(userId)){
//            gsUser.setRoles(gsRoleMapper.selectGsRoleList(null));
//        }else {
//            if(longs.length>0)
//            gsUser.setRoles(gsRoleMapper.selectGsRoleByUserId(longs));
//        }
        return gsUser;
    }

    /**
     * 查询小程序用户列表
     * 
     * @param gsUser 小程序用户
     * @return 小程序用户
     */
    @Override
    public List<GsUser> selectGsUserList(GsUser gsUser)
    {
        return gsUserMapper.selectGsUserList(gsUser);
    }

    /**
     * 新增小程序用户
     * 
     * @param gsUser 小程序用户
     * @return 结果
     */
    @Transactional
    @Override
    public int insertGsUser(GsUser gsUser)
    {
        int rows = gsUserMapper.insertGsUser(gsUser);
        insertUserRole(gsUser);
        return rows;
    }

    /**
     * 修改小程序用户
     * 
     * @param gsUser 小程序用户
     * @return 结果
     */
    @Transactional
    @Override
    public int updateGsUser(GsUser gsUser)
    {
        Long userId = gsUser.getUserId();
        // 删除用户与角色关联
        gsRoleMapper.deleteUserRoleByUserId(userId);
        // 新增用户与角色管理
        insertUserRole(gsUser);
        return gsUserMapper.updateGsUser(gsUser);
    }

    /**
     * 新增用户角色信息
     *
     * @param user 用户对象
     */
    public void insertUserRole(GsUser user)
    {
        Long[] roles = user.getRoleIds();
        if (StringUtils.isNotNull(roles))
        {
            // 新增用户与角色管理
            List<GsUserPermission> list = new ArrayList<GsUserPermission>();
            for (Long roleId : roles)
            {
                GsUserPermission ur = new GsUserPermission();
                ur.setUserId(user.getUserId());
                ur.setRoleId(roleId);
                list.add(ur);
            }
            if (list.size() > 0)
            {
                gsRoleMapper.batchUserRole(list);
            }
        }
    }

    /**
     * 批量删除小程序用户
     * 
     * @param userIds 需要删除的小程序用户ID
     * @return 结果
     */
    @Transactional
    @Override
    public int deleteGsUserByIds(Long[] userIds)
    {
        gsUserMapper.deleteGsUserIntegralLogByUserIds(userIds);
        return gsUserMapper.deleteGsUserByIds(userIds);
    }

    /**
     * 删除小程序用户信息
     * 
     * @param userId 小程序用户ID
     * @return 结果
     */
    @Override
    public int deleteGsUserById(Long userId)
    {
        gsUserMapper.deleteGsUserIntegralLogByUserId(userId);
        return gsUserMapper.deleteGsUserById(userId);
    }

    /**
     * 新增积分记录信息
     * 
     * @param gsUser 小程序用户对象
     */
    public void insertGsUserIntegralLog(GsUser gsUser)
    {
        List<GsUserIntegralLog> gsUserIntegralLogList = gsUser.getGsUserIntegralLogList();
        Long userId = gsUser.getUserId();
        if (StringUtils.isNotNull(gsUserIntegralLogList))
        {
            List<GsUserIntegralLog> list = new ArrayList<GsUserIntegralLog>();
            for (GsUserIntegralLog gsUserIntegralLog : gsUserIntegralLogList)
            {
                gsUserIntegralLog.setUserId(userId);
                list.add(gsUserIntegralLog);
            }
            if (list.size() > 0)
            {
                gsUserMapper.batchGsUserIntegralLog(list);
            }
        }
    }
}
