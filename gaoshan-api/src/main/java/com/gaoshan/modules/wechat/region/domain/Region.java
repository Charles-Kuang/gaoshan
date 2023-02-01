package com.gaoshan.modules.wechat.region.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("gs_region")
public class Region {
    @TableId(value = "region_id", type = IdType.AUTO)
    private String regionId;
    private String parentCode;
    private String parentName;
    private String forefathers;
    private String police;
    private String code;
    private String name;
    private String fullName;
    private String firstLetter;
    private String lng;
    private String lat;
    private String geographicGrid;
    private String geographicEnity;
    private String type;
    private String hasChild;
    private String level;
    private String createBy;
    private String createTime;
    private String updateBy;
    private String updateTime;
}
