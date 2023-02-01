package com.ruoyi.core.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 积分记录对象 gs_user_integral_log
 *
 * @author ruoyi
 * @date 2022-12-01
 */
public class GsUserIntegralLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** 交易名称 */
    @Excel(name = "交易名称")
    private String name;

    /** 交易时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "交易时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date tradTime;

    /** 积分 */
    @Excel(name = "积分")
    private Long credits;

    /** 积分类型 */
    @Excel(name = "积分类型")
    private String type;

    /** 关联用户Id */
    @Excel(name = "关联用户Id")
    private Long userId;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setTradTime(Date tradTime)
    {
        this.tradTime = tradTime;
    }

    public Date getTradTime()
    {
        return tradTime;
    }
    public void setCredits(Long credits)
    {
        this.credits = credits;
    }

    public Long getCredits()
    {
        return credits;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("tradTime", getTradTime())
                .append("credits", getCredits())
                .append("type", getType())
                .append("userId", getUserId())
                .append("remark", getRemark())
                .toString();
    }
}
