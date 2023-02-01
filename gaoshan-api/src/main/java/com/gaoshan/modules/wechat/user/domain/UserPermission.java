package com.gaoshan.modules.wechat.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.List;
@Data
@TableName("gs_user_permission")
public class UserPermission {
    private Integer userId;
    @TableField(exist = false)
    private User user;
    private Integer roleId;
    @TableField(exist = false)
    private List<Role> roleList;

}
