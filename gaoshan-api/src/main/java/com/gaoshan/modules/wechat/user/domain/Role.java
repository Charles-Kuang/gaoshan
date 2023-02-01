package com.gaoshan.modules.wechat.user.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("gs_role")
public class Role {
    @TableId(value = "role_id", type = IdType.AUTO)
    private String roleId;
    private String roleName;
    private String roleKey;
    private String roleSort;
    private String dataScope;
    private String menuCheckStrictly;
    private String deptCheckStrictly;
    private String status;
    private String delFlag;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
    private String remark;
}
