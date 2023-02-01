package com.gaoshan.modules.wechat.user.mapping;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gaoshan.modules.wechat.user.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
public interface WxMinUserMapper extends BaseMapper<User> {

    void register(Map json);

    User findUserByPhone(String phone);
}
