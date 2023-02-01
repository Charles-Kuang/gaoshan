package com.ruoyi.journal.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 接口日志对象 gs_interface_log
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public class GsInterfaceLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 授权开关 0false 1true */
    @Excel(name = "授权开关 0false 1true")
    private Long authFlag;

    /** 请求路由 */
    @Excel(name = "请求路由")
    private String reqUrl;

    /** 请求参数 */
    @Excel(name = "请求参数")
    private String reqParams;

    /** 返回参数 */
    @Excel(name = "返回参数")
    private String resultParams;

    /** 请求方法 */
    @Excel(name = "请求方法")
    private String reqMethod;

    /** 请求描述 */
    @Excel(name = "请求描述")
    private String executeRemark;

    /** 执行时间 */
    @Excel(name = "执行时间")
    private Long executeTime;

    /** 请求ip */
    @Excel(name = "请求ip")
    private String reqIp;

    /** 执行异常描述 */
    @Excel(name = "执行异常描述")
    private String excetionRemark;

    /** token授权值 */
    @Excel(name = "token授权值")
    private String token;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAuthFlag(Long authFlag) 
    {
        this.authFlag = authFlag;
    }

    public Long getAuthFlag() 
    {
        return authFlag;
    }
    public void setReqUrl(String reqUrl) 
    {
        this.reqUrl = reqUrl;
    }

    public String getReqUrl() 
    {
        return reqUrl;
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
    public void setExecuteTime(Long executeTime) 
    {
        this.executeTime = executeTime;
    }

    public Long getExecuteTime() 
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
    public void setExcetionRemark(String excetionRemark) 
    {
        this.excetionRemark = excetionRemark;
    }

    public String getExcetionRemark() 
    {
        return excetionRemark;
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
            .append("authFlag", getAuthFlag())
            .append("reqUrl", getReqUrl())
            .append("reqParams", getReqParams())
            .append("resultParams", getResultParams())
            .append("reqMethod", getReqMethod())
            .append("executeRemark", getExecuteRemark())
            .append("executeTime", getExecuteTime())
            .append("reqIp", getReqIp())
            .append("createTime", getCreateTime())
            .append("excetionRemark", getExcetionRemark())
            .append("token", getToken())
            .toString();
    }
}
