package com.gaoshan.aspect.flow.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author lumberer
 * 错误异常记录
 */
@Data
@TableName("gs_error_log")
public class ExceptionLogParam implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    /**
     * 请求异常方法
     */
    private String reqMethod;
    /**
     * 描述
     */
    private String executeRemark;
    /**
     * 请求参数
     */
    private String reqParams;
    /**
     * 返回参数
     */
    private String resultParams;
    /**
     * 执行时间
     */
    private Double executeTime;
    /**
     * 请求IP
     */
    private String reqIp;
    /**
     * 创建发生时间
     */
    private Date createTime;
    /**
     * 异常错误描述
     */
    private String exceptionRemark;
    /**
     * 请求token
     */
    private String token;
}
