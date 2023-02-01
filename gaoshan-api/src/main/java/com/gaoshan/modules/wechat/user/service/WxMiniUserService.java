package com.gaoshan.modules.wechat.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gaoshan.util.Result;
import com.gaoshan.modules.wechat.user.domain.User;

public interface WxMiniUserService extends IService<User> {

    Result phoneLogin(String code,String phone);

    Result userUpdate(User user);

    Result getUserPermission();
}
