package com.ruoyi.applet.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.AuditEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;

/**
 * 实有单位列对象 gs_unit
 *
 * @author ruoyi
 * @date 2022-12-01
 */
@Data
public class GsUnit extends AuditEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long unitId;

    /** 地址 */
    @Excel(name = "地址")
    private Long addressId;

    /** 完整详细地址 */
    @Excel(name = "完整详细地址")
    private String addressDetail;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String name;

    /** 组织机构代码 */
    @Excel(name = "组织机构代码")
    private String organizeCode;

    /** 公司联系电话 */
    @Excel(name = "公司联系电话")
    private String companyPhone;

    /** 公司门头照 */
    @Excel(name = "公司门头照")
    private String doorImage;

    /** 营业执照图 */
    @Excel(name = "营业执照图")
    private String businessLicense;

    /** 经营范围 */
    @Excel(name = "经营范围")
    private String businessScope;

    /** 单位法人 */
    @Excel(name = "单位法人")
    private String legalPerson;

    /** 法人联系电话 */
    @Excel(name = "法人联系电话")
    private String legalPersonPhone;

    /** 法人身份证号 */
    @Excel(name = "法人身份证号")
    private String corporateIdentityCard;

    /** 单位类别 */
    @Excel(name = "单位类别")
    private String type;

    /** 单位性质 */
    @Excel(name = "单位性质")
    private String nature;

    /** 负责人联系电话 */
    @Excel(name = "负责人联系电话")
    private String principalMobile;

    /** 单位成立日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "单位成立日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date establishDay;

    /** 数据状态 */
    @Excel(name = "数据状态")
    private String dataState;

}