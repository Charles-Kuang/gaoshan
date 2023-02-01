package com.gaoshan.modules.wechat.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.modules.wechat.user.domain.Role;
import com.gaoshan.modules.wechat.user.mapping.RoleMapper;
import com.gaoshan.modules.wechat.user.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
}
