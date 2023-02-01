package com.ruoyi.applet.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.AuditEntity;
import lombok.Data;

import java.util.Date;

/**
 * 实有人口登记对象 gs_person
 * 
 * @author ruoyi
 * @date 2022-11-26
 */
@TableName("gs_person")
@Data
public class GsPerson extends AuditEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long personId;

    /** 地址id */
    @Excel(name = "地址id")
    private Long addressId;

    /** 详细完整地址 */
    @Excel(name = "详细完整地址")
    private String addressDetail;

    /** 证件类型 */
    @Excel(name = "证件类型")
    private String identityType;

    /** 身份证反面照 */
    @Excel(name = "身份证反面照")
    private String identityReverse;

    /** 身份证正面照 */
    @Excel(name = "身份证正面照")
    private String identityFront;

    /** 证件号码 */
    @Excel(name = "证件号码")
    private String identityNumber;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 曾用名 */
    @Excel(name = "曾用名")
    private String usedName;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 人员照片 */
    @Excel(name = "人员照片")
    private String personImg;

    /** 英文名 */
    @Excel(name = "英文名")
    private String englishName;

    /** 人员类别 */
    @Excel(name = "人员类别")
    private String personType;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long age;

    /** 民族 */
    @Excel(name = "民族")
    private String nation;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /** 籍贯地址id */
    @Excel(name = "籍贯地址id")
    private String nativePlaceAddressId;

    /** 籍贯完整地址 */
    @Excel(name = "籍贯完整地址")
    private String nativePlaceAddressDetail;

    /** 职业 */
    @Excel(name = "职业")
    private String job;

    /** 文化程度 */
    @Excel(name = "文化程度")
    private String education;

    /** 现工作单位id */
    @Excel(name = "现工作单位id")
    private String nowUnitId;

    /** 婚姻状况 */
    @Excel(name = "婚姻状况")
    private String marital;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String phoneNumber;

    /** 暂住事由 */
    @Excel(name = "暂住事由")
    private String reason;

    /** 从业情况 */
    @Excel(name = "从业情况")
    private String employment;

    /** 数据状态 */
    @Excel(name = "数据状态")
    private String dataState;

    /** 关注程度[重点人员类别] */
    @Excel(name = "关注程度[重点人员类别]")
    private Long attentionLevel;

    /** 知名人士类型 */
    @Excel(name = "知名人士类型")
    private Long celebrity;

    /** 是否帮扶救助对象 */
    @Excel(name = "是否帮扶救助对象")
    private String supportObject;

    /** 特长备注 */
    @Excel(name = "特长备注")
    private String specialty;

    /** 交通工具 */
    @Excel(name = "交通工具")
    private String vehicle;
}
