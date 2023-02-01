package com.gaoshan.aspect.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("gs_interface_log")
public class InterfaceLog {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String reqMethod;
    private Boolean authFlag;
    private String executeRemark;
    private String reqParams;
    private String resultParams;
    private Double executeTime;
    private String reqIp;
    private String reqUrl;
    private Date createTime;
    private String excetionRemark;
    private String token;
}
