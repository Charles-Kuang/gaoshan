package com.ruoyi.journal.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 错误日志对象 gs_error_log
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public class GsErrorLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 报错方法 */
    @Excel(name = "报错方法")
    private String reqMethod;

    /** 报错描述 */
    @Excel(name = "报错描述")
    private String executeRemark;

    /** 请求参数 */
    @Excel(name = "请求参数")
    private String reqParams;

    /** 返回参数 */
    @Excel(name = "返回参数")
    private String resultParams;

    /** 执行时间 */
    @Excel(name = "执行时间")
    private BigDecimal executeTime;

    /** 请求IP */
    @Excel(name = "请求IP")
    private String reqIp;

    /** 异常错误描述 */
    @Excel(name = "异常错误描述")
    private String exceptionRemark;

    /** 请求token */
    @Excel(name = "请求token")
    private String token;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setReqMethod(String reqMethod) 
    {
        this.reqMethod = reqMethod;
    }

    public String getReqMethod() 
    {
        return reqMethod;
    }
    public void setExecuteRemark(String executeRemark) 
    {
        this.executeRemark = executeRemark;
    }

    public String getExecuteRemark() 
    {
        return executeRemark;
    }
    public void setReqParams(String reqParams) 
    {
        this.reqParams = reqParams;
    }

    public String getReqParams() 
    {
        return reqParams;
    }
    public void setResultParams(String resultParams) 
    {
        this.resultParams = resultParams;
    }

    public String getResultParams() 
    {
        return resultParams;
    }
    public void setExecuteTime(BigDecimal executeTime) 
    {
        this.executeTime = executeTime;
    }

    public BigDecimal getExecuteTime() 
    {
        return executeTime;
    }
    public void setReqIp(String reqIp) 
    {
        this.reqIp = reqIp;
    }

    public String getReqIp() 
    {
        return reqIp;
    }
    public void setExceptionRemark(String exceptionRemark) 
    {
        this.exceptionRemark = exceptionRemark;
    }

    public String getExceptionRemark() 
    {
        return exceptionRemark;
    }
    public void setToken(String token) 
    {
        this.token = token;
    }

    public String getToken() 
    {
        return token;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("reqMethod", getReqMethod())
            .append("executeRemark", getExecuteRemark())
            .append("reqParams", getReqParams())
            .append("resultParams", getResultParams())
            .append("executeTime", getExecuteTime())
            .append("reqIp", getReqIp())
            .append("createTime", getCreateTime())
            .append("exceptionRemark", getExceptionRemark())
            .append("token", getToken())
            .toString();
    }
}
