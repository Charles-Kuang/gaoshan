package com.gaoshan.modules.wechat.user.controller;

import com.gaoshan.aspect.auth.LoginAuth;
import com.gaoshan.util.Result;
import com.gaoshan.modules.wechat.user.domain.User;
import com.gaoshan.modules.wechat.user.service.WxMiniUserService;
import com.gaoshan.util.TokenUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 微信小程序微信控制类
 */
@RestController
@RequestMapping("/wxmini")
@Api(tags="微信小程序用户相关接口")
public class WxMiniUserController {
    @Autowired
    private WxMiniUserService wxMiniUserService;

    @LoginAuth(flag = false,remark = "小程序根据手机code自动登录接口，或者传入token刷新token接口")
    @GetMapping("/openIdInput")
    public Result openIdInput(String jsCode){
        return Result.SUCCESS("");
    }
    /**
     * 手机code进行登录
     * @param code
     * @return
     */
    @LoginAuth(flag = false,remark = "小程序根据手机code自动登录接口，或者传入token刷新token接口")
    @GetMapping("/phoneLogin")
    @ApiOperation("通过code注册登录获取token，传入token时刷新token，返回token")
    public Result phonLogin(@ApiParam("通过微信授权获取到的code") String code,@ApiParam("例外，调试用手机号直接登陆")String phone){
            return wxMiniUserService.phoneLogin(code,phone);
    }

    /**
     * 完善用户信息
     * @return
     */
    @LoginAuth(remark = "更新完善个人信息")
    @PostMapping("/userUpdate")
    @ApiOperation("传入个人信息和token进行更新")
    public Result userUpdate(@RequestBody User user){
       return wxMiniUserService.userUpdate(user);
    }

    /**
     * 获取当前登录的用户信息
     * @return
     */
    @LoginAuth(remark = "传入token获取当前登录的用户个人信息")
    @GetMapping("/userInfo")
    @ApiOperation("传入token获取当前登录的用户个人信息")
    public Result getUserData(){
        User user=TokenUtil.getTokenUser();
        return Result.SUCCESS(user);
    }

    /**
     * 获取当前用户是否权限角色
     * @return
     */
    @LoginAuth(remark = "获取用户拥有的权限")
    @GetMapping("/userPermission")
    @ApiOperation("判断用户是否拥有权限")
    public Result userPermission(){
        return wxMiniUserService.getUserPermission();
    }
}
