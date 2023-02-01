package com.gaoshan.modules.wechat.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author lumberer
 * @date 2022-11-19
 * 前端用户相关表
 */
@ApiModel("用户信息")
@Data
@TableName("gs_user")
public class User {
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("积分")
    private Integer integral;
    @ApiModelProperty("性别 1 男 2 女 3未知")
    private Integer gender;
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("真实姓名")
    private String realName;
    @ApiModelProperty("用户头像")
    private String avatar;
    @ApiModelProperty("小程序openid")
    private String miniOpenId;
    /**
     * 来源地id，自动检索
     */
    private String sourceId;
    @ApiModelProperty("户籍地地址")
    private String sourceAddress;
    /**
     * 房屋ID，自动检索
     */
    private String houseId;
    @ApiModelProperty("现居住地址")
    private String houseAddress;
    @ApiModelProperty("用户身份证号")
    private String idNumber;
    @ApiModelProperty("注册时间")
    private Date registerTime;
    @ApiModelProperty("最后登录时间")
    private String lastLoginTime;


}
