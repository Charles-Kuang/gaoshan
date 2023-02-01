package com.gaoshan.modules.test;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.modules.wechat.user.domain.User;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date
 */
@Service
public class TestServiceImpl extends ServiceImpl<UserDao, User>  implements TestService{
}
