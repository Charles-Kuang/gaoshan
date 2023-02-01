package com.gaoshan.modules.wechat.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaoshan.modules.wechat.user.domain.UserPermission;

import java.util.List;

/**
 * 角色权限
 */
public interface UserPermissionService extends IService<UserPermission> {
    /**
     * 根据用户ID获取所拥有的权限集合
     * @return
     */
    public UserPermission userPermissionList(Integer userId);
}
