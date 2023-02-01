package com.ruoyi.core.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 小程序用户对象 gs_user
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public class GsUser extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long userId;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 积分余额 */
    @Excel(name = "积分余额")
    private Long integral;

    /** 性别 */
    @Excel(name = "性别")
    private Long gender;

    /** 昵称 */
    @Excel(name = "昵称")
    private String nickName;

    /** 真实姓名 */
    @Excel(name = "真实姓名")
    private String realName;

    /** 头像地址 */
    @Excel(name = "头像地址")
    private String avatar;

    /** 小程序openid */
    @Excel(name = "小程序openid")
    private Long miniOpenId;

    /** 来源地区id */
    @Excel(name = "来源地区id")
    private Long sourceId;

    /** 来源区域地址 */
    @Excel(name = "来源区域地址")
    private String sourceAddress;

    /** 现居住房屋id(门牌号） */
    @Excel(name = "现居住房屋id(门牌号）")
    private Long houseId;

    /** 现居住地址完整地址 */
    @Excel(name = "现居住地址完整地址")
    private String houseAddress;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idNumber;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date registerTime;

    /** 最后一次登录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后一次登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastLoginTime;

    /** 是否入库 */
    @Excel(name = "是否入库")
    private Integer entering;
    
    /** 角色对象 */
    private List<GsRole> roles;

    /** 角色组 */
    private Long[] roleIds;

    /** 积分记录信息 */
    private List<GsUserIntegralLog> gsUserIntegralLogList;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setIntegral(Long integral) 
    {
        this.integral = integral;
    }

    public Long getIntegral() 
    {
        return integral;
    }
    public void setGender(Long gender) 
    {
        this.gender = gender;
    }

    public Long getGender() 
    {
        return gender;
    }
    public void setNickName(String nickName) 
    {
        this.nickName = nickName;
    }

    public String getNickName() 
    {
        return nickName;
    }
    public void setRealName(String realName) 
    {
        this.realName = realName;
    }

    public String getRealName() 
    {
        return realName;
    }
    public void setAvatar(String avatar) 
    {
        this.avatar = avatar;
    }

    public String getAvatar() 
    {
        return avatar;
    }
    public void setMiniOpenId(Long miniOpenId) 
    {
        this.miniOpenId = miniOpenId;
    }

    public Long getMiniOpenId() 
    {
        return miniOpenId;
    }
    public void setSourceId(Long sourceId) 
    {
        this.sourceId = sourceId;
    }

    public Long getSourceId() 
    {
        return sourceId;
    }
    public void setSourceAddress(String sourceAddress) 
    {
        this.sourceAddress = sourceAddress;
    }

    public String getSourceAddress() 
    {
        return sourceAddress;
    }
    public void setHouseId(Long houseId) 
    {
        this.houseId = houseId;
    }

    public Long getHouseId() 
    {
        return houseId;
    }
    public void setHouseAddress(String houseAddress) 
    {
        this.houseAddress = houseAddress;
    }

    public String getHouseAddress() 
    {
        return houseAddress;
    }
    public void setIdNumber(String idNumber) 
    {
        this.idNumber = idNumber;
    }

    public String getIdNumber() 
    {
        return idNumber;
    }
    public void setRegisterTime(Date registerTime) 
    {
        this.registerTime = registerTime;
    }

    public Date getRegisterTime() 
    {
        return registerTime;
    }
    public void setLastLoginTime(Date lastLoginTime) 
    {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastLoginTime() 
    {
        return lastLoginTime;
    }
    public void setEntering(Integer entering) 
    {
        this.entering = entering;
    }

    public Integer getEntering() 
    {
        return entering;
    }

    public List<GsUserIntegralLog> getGsUserIntegralLogList()
    {
        return gsUserIntegralLogList;
    }

    public void setGsUserIntegralLogList(List<GsUserIntegralLog> gsUserIntegralLogList)
    {
        this.gsUserIntegralLogList = gsUserIntegralLogList;
    }

    public List<GsRole> getRoles()
    {
        return roles;
    }

    public void setRoles(List<GsRole> roles)
    {
        this.roles = roles;
    }

    public Long[] getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds)
    {
        this.roleIds = roleIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("phone", getPhone())
            .append("integral", getIntegral())
            .append("gender", getGender())
            .append("nickName", getNickName())
            .append("realName", getRealName())
            .append("avatar", getAvatar())
            .append("miniOpenId", getMiniOpenId())
            .append("sourceId", getSourceId())
            .append("sourceAddress", getSourceAddress())
            .append("houseId", getHouseId())
            .append("houseAddress", getHouseAddress())
            .append("idNumber", getIdNumber())
            .append("registerTime", getRegisterTime())
            .append("lastLoginTime", getLastLoginTime())
            .append("entering", getEntering())
            .append("gsUserIntegralLogList", getGsUserIntegralLogList())
            .append("roles", getRoles())
            .append("roleIds", getRoleIds())
            .toString();
    }
}
