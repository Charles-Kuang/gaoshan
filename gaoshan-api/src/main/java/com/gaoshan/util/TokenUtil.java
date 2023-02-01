package com.gaoshan.util;

import com.gaoshan.config.RedisCache;
import com.gaoshan.enumeration.TOKEN_TYPE;
import com.gaoshan.modules.wechat.user.domain.User;
import com.gaoshan.modules.wechat.util.cache.CacheServiceImpl;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @date
 * token工具类
 */
@Component
public class TokenUtil {
    @Autowired
    private RedisCache cache;
    private static TokenUtil tokenUtil;
    @PostConstruct
    public void init(){
        tokenUtil = this;
        tokenUtil.cache = this.cache;
    }

    private static Integer cacheTime;
    @Value("${token.time}")
    public void setCacheTime(Integer cacheTime){
        TokenUtil.cacheTime =cacheTime;
    }
    /**
     * 获取token信息
     * @return
     */
    public static String getToken(){
        ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getHeader(TOKEN_TYPE.TOKEN.name());
    }

    /**
     * 根据token获取用户信息
     * @return
     */
    public static User getTokenUser(){
        String token=getToken();
        StringBuffer tokenStr=new StringBuffer(TOKEN_TYPE.TOKEN.getValue());
        tokenStr.append(token);
        User user=tokenUtil.cache.getCacheObject(tokenStr.toString());
        return user;
    }

    /**
     * 更新token
     * @param user
     */
    public static void tokenUserUpdate(User user){
        String token=getToken();
        if(StringUtils.isNotEmpty(token)){
            StringBuffer tokenStr=new StringBuffer(TOKEN_TYPE.TOKEN.getValue());
            tokenStr.append(token);
            tokenUtil.cache.putCacheObject(tokenStr.toString(),user,cacheTime);
        }

    }
}
