package com.gaoshan.modules.wechat.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.modules.wechat.user.domain.Role;
import com.gaoshan.modules.wechat.user.domain.User;
import com.gaoshan.modules.wechat.user.domain.UserPermission;
import com.gaoshan.modules.wechat.user.mapping.UserPermissionMapper;
import com.gaoshan.modules.wechat.user.service.RoleService;
import com.gaoshan.modules.wechat.user.service.UserPermissionService;
import com.gaoshan.modules.wechat.user.service.WxMiniUserService;
import com.gaoshan.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserPermissionServiceImpl extends ServiceImpl<UserPermissionMapper,UserPermission> implements UserPermissionService {
    @Autowired
    RoleService roleService;
    @Autowired
    WxMiniUserService userService;
    @Override
    public UserPermission userPermissionList(Integer userId) {
        QueryWrapper<UserPermission> queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        List<UserPermission> userPermissionList=this.getBaseMapper().selectList(queryWrapper);
        List<Integer> roleListId=new ArrayList<>();
        userPermissionList.stream().forEach(item->{
            roleListId.add(item.getRoleId());
        });
        UserPermission userPermission=new UserPermission();
        userPermission.setUser(userService.getById(userId));
        userPermission.setUserId(userId);
        List<Role> roleList=roleService.getBaseMapper().selectBatchIds(roleListId);
        userPermission.setRoleList(roleList);
        return userPermission;
    }
}
