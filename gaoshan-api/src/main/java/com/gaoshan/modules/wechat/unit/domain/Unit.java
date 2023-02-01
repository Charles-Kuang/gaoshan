package com.gaoshan.modules.wechat.unit.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gaoshan.modules.wechat.common.domain.Common;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("实有单位实体类")
@Data
@TableName("gs_unit")
public class Unit extends Common {
    @ApiModelProperty("唯一标识id")
    @TableId(value = "unit_id", type = IdType.AUTO)
    private String unitId;
    @ApiModelProperty("通过查询获取到的地址id，无则不传入")
    private String addressId;
    @ApiModelProperty("地址详情")
    private String addressDetail;
    @ApiModelProperty("单位名称")
    private String name;
    @ApiModelProperty("单位法人")
    private String legalPerson;
    @ApiModelProperty("单位法人联系电话")
    private String legalPersonPhone;
    @ApiModelProperty("单位法人身份证")
    private String corporateIdentityCard;
    @ApiModelProperty("单位类型")
    private String type;
    @ApiModelProperty("单位性质")
    private String nature;
    @ApiModelProperty("组织机构代码")
    private String organizeCode;
    @ApiModelProperty("负责人联系电话")
    private String principalMobile;
    @ApiModelProperty("单位成立日期")
    private Date establishDay;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("公司联系电话")
    private String companyPhone;
    @ApiModelProperty("门头照")
    private String doorImage;
    @ApiModelProperty("营业执照图")
    private String businessLicense;
    @ApiModelProperty("经营范围")
    private String businessScope;
    @TableField(exist = false)
    @ApiModelProperty("区域/居委会id")
    private Integer regionId;
    @TableField(exist = false)
    @ApiModelProperty("经度")
    private Double lng;
    @TableField(exist = false)
    @ApiModelProperty("纬度")
    private Double lat;
}
