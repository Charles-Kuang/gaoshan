package com.gaoshan.modules.wechat.user.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gaoshan.config.RedisCache;
import com.gaoshan.enumeration.HTTP_STATUS_CODE_ENUM;
import com.gaoshan.enumeration.TOKEN_TYPE;
import com.gaoshan.modules.wechat.init.WechatMsg;
import com.gaoshan.modules.wechat.user.domain.User;
import com.gaoshan.modules.wechat.user.domain.UserPermission;
import com.gaoshan.modules.wechat.user.mapping.WxMinUserMapper;
import com.gaoshan.modules.wechat.user.service.UserPermissionService;
import com.gaoshan.modules.wechat.user.service.WxMiniUserService;
import com.gaoshan.modules.wechat.util.cache.CacheService;
import com.gaoshan.modules.wechat.util.cache.CacheServiceImpl;
import com.gaoshan.util.Result;
import com.gaoshan.util.TokenUtil;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author lumberer
 */
@Service
public class WxMiniUserServiceImpl extends ServiceImpl<WxMinUserMapper,User> implements WxMiniUserService {
    @Resource
    private WxMinUserMapper wxMinUserMapper;
    @Autowired
    UserPermissionService userPermissionService;
    @Resource
    RedisCache cache;
//    @Resource
//    CacheService cache;
    @Value("${token.time}")
    private Integer cacheTime;
    @Override
    public Result phoneLogin(String code,String phones) {
        ServletRequestAttributes servletRequestAttributes =  (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        //暂时
        StringBuffer tokenType=new StringBuffer(TOKEN_TYPE.TOKEN.getValue());
        User user=null;
        String token= TokenUtil.getToken();
        if(StringUtils.isNotEmpty(token)){
            //已存在的用户，使用token进行请求
            tokenType.append(token);
            user=cache.getCacheObject(tokenType.toString());
            if(cache.isContainsKey(tokenType.toString())&&cache.isTimeOut(tokenType.toString())){
                tokenType=new StringBuffer(TOKEN_TYPE.TOKEN.getValue());
                //登录已过期，刷新token
                token=UUID.randomUUID().toString().replaceAll("-","");
                if(null==user){
                    JSONObject json = WechatMsg.getPhoneNumber(code);
                    if(0!=json.getInteger("errcode")){
                        return Result.ERROR(json.getString("errmsg"),json.getInteger("errcode"),json);
                    }
                    JSONObject phoneInfoJson=json.getJSONObject("phone_info");
                    String phone=phoneInfoJson.getString("purePhoneNumber");
                    user=wxMinUserMapper.findUserByPhone(phone);
                }
                cache.putCacheObject(tokenType.toString(),user,cacheTime);
            }else{
                JSONObject json = WechatMsg.getPhoneNumber(code);
                if(0!=json.getInteger("errcode")){
                    return Result.ERROR(json.getString("errmsg"),json.getInteger("errcode"),json);
                }
                JSONObject phoneInfoJson=json.getJSONObject("phone_info");
                String phone=phoneInfoJson.getString("purePhoneNumber");
                user=wxMinUserMapper.findUserByPhone(phone);
                cache.putCacheObject(tokenType.toString(),user,cacheTime);
            }
        }else{
            try{
                if(StringUtils.isNotEmpty(phones)){
                    QueryWrapper queryWrapper=new QueryWrapper();
                    queryWrapper.eq("phone",phones);
                     user=wxMinUserMapper.selectOne(queryWrapper);
                     if(null==user){
                         //新登录用户
                         token=UUID.randomUUID().toString().replaceAll("-","");
                         user=new User();
                         user.setPhone(phones);
                         wxMinUserMapper.insert(user);
                     }
                }else{
                    JSONObject json = WechatMsg.getPhoneNumber(code);
                    if(0!=json.getInteger("errcode")){
                        return Result.ERROR(json.getString("errmsg"),json.getInteger("errcode"),json);
                    }
                    JSONObject phoneInfoJson=json.getJSONObject("phone_info");
                    String phone=phoneInfoJson.getString("purePhoneNumber");
                    user=wxMinUserMapper.findUserByPhone(phone);

                    if(user==null){
                        //新登录用户
                        token=UUID.randomUUID().toString().replaceAll("-","");
                        user=new User();
                        user.setPhone(phone);
                        wxMinUserMapper.insert(user);
                    }
                }
                if(StringUtils.isEmpty(token)){
                    token=UUID.randomUUID().toString().replaceAll("-","");
                }
                tokenType.append(token);

                cache.putCacheObject(tokenType.toString(),user,cacheTime);
            }catch (Exception e){
                e.printStackTrace();
                response.setStatus(403);
                return Result.ERROR("授权信息错误，请先获取授权信息！",403,e.getMessage());
            }

        }
        if(null!=user){
            String nowDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            user.setLastLoginTime(nowDate);
            wxMinUserMapper.updateById(user);
        }

        return Result.SUCCESS(token);
    }

    @Override
    public Result userUpdate(User user) {
        String token= TokenUtil.getToken();
        User user2=cache.getCacheObject(TOKEN_TYPE.TOKEN.getValue()+token);
        if(null==user2){
            return Result.ERROR(HTTP_STATUS_CODE_ENUM.TOKEN_NOT_FIND);
        }
        user.setUserId(user2.getUserId());
        wxMinUserMapper.updateById(user);
        User userCache=wxMinUserMapper.selectById(user2.getUserId());
        TokenUtil.tokenUserUpdate(userCache);
        return Result.SUCCESS(token,"更新成功！");
    }

    @Override
    public Result getUserPermission() {
        User user=TokenUtil.getTokenUser();
        QueryWrapper<UserPermission> queryWrapper=new QueryWrapper();
        queryWrapper.eq("user_id",user.getUserId());
        List<UserPermission> userPermission=userPermissionService.getBaseMapper().selectList(queryWrapper);
        if(userPermission.size()>0){
            return Result.SUCCESS(userPermission);
        }else{
            return Result.ERROR(HTTP_STATUS_CODE_ENUM.NO_PERMISSION);
        }
    }
}
