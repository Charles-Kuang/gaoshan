package com.ruoyi.core.domain;

import com.ruoyi.common.core.domain.entity.GsRegion;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 前台角色对象 gs_role
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public class GsRole extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 角色 */
    private Long roleId;

    /** 角色名称 */
    @Excel(name = "角色名称")
    private String roleName;

    /** 角色权限字符串 */
    @Excel(name = "角色权限字符串")
    private String roleKey;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    private Long roleSort;

    /** 数据范围(多个数据用逗号隔开) */
    @Excel(name = "数据范围")
    private String dataScope;

    /** 角色状态 */
    @Excel(name = "角色状态")
    private Long roleState;

    /** 数据状态 */
    @Excel(name = "数据状态")
    private Long dataState;

    /** 区域组（数据权限） */
    private Long[] regionIds;

    /** 管辖区域 */
    private List<GsRegion> children = new ArrayList<GsRegion>();

    public void setRoleId(Long roleId) 
    {
        this.roleId = roleId;
    }

    public Long getRoleId() 
    {
        return roleId;
    }
    public void setRoleName(String roleName) 
    {
        this.roleName = roleName;
    }

    public String getRoleName() 
    {
        return roleName;
    }
    public void setRoleKey(String roleKey) 
    {
        this.roleKey = roleKey;
    }

    public String getRoleKey() 
    {
        return roleKey;
    }
    public void setRoleSort(Long roleSort) 
    {
        this.roleSort = roleSort;
    }

    public Long getRoleSort() 
    {
        return roleSort;
    }
    public void setDataScope(String dataScope) 
    {
        this.dataScope = dataScope;
    }

    public String getDataScope() 
    {
        return dataScope;
    }
    public void setRoleState(Long roleState) 
    {
        this.roleState = roleState;
    }

    public Long getRoleState() 
    {
        return roleState;
    }
    public void setDataState(Long dataState) 
    {
        this.dataState = dataState;
    }

    public Long getDataState() 
    {
        return dataState;
    }

    public Long[] getRegionIds() {
        return regionIds;
    }

    public void setRegionIds(Long[] regionIds) {
        this.regionIds = regionIds;
    }

    public List<GsRegion> getChildren() {
        return children;
    }

    public void setChildren(List<GsRegion> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("roleId", getRoleId())
            .append("roleName", getRoleName())
            .append("roleKey", getRoleKey())
            .append("roleSort", getRoleSort())
            .append("dataScope", getDataScope())
            .append("roleState", getRoleState())
            .append("dataState", getDataState())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("regionIds",getRegionIds())
            .append("children",getChildren())
            .toString();
    }
}
