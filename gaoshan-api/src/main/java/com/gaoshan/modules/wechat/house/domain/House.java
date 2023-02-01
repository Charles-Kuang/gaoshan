package com.gaoshan.modules.wechat.house.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gaoshan.modules.wechat.common.domain.Common;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("实有房屋实体类")
@Data
@TableName("gs_house")
public class House extends Common {
    @TableId(value = "house_id", type = IdType.AUTO)
    private Integer houseId;
    @ApiModelProperty("通过查询获取到的地址id，无则不传入")
    private Integer addressId;
    @ApiModelProperty("详细完整地址")
    private String addressDetail;
    @ApiModelProperty("房间数")
    private String houseNumber;
    @ApiModelProperty("房屋别名")
    private String anotherName;
    @ApiModelProperty("居住人数")
    private String dwellPersonNumber;
    @ApiModelProperty("产权人姓名")
    private String propertyPerson;
    @ApiModelProperty("产权人身份证号")
    private String propertyPersonIdentityNumber;
    @ApiModelProperty("产权人现居住地址")
    private String propertyPersonDwellAddress;
    @ApiModelProperty("产权人联系电话")
    private String propertyPersonPhone;
    @ApiModelProperty("房屋类型")
    private String houseType;
    @ApiModelProperty("关注级别")
    private String attentionLevel;
    @ApiModelProperty("房屋面积")
    private String houseArea;
    @ApiModelProperty("房屋用途")
    private String  houseUse;
    @ApiModelProperty("门头照")
    private String doorImage;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("数据状态")
    private Integer dataState;
    @ApiModelProperty("区域/居委会id")
    @TableField(exist = false)
    private Integer regionId;
    @TableField(exist = false)
    @ApiModelProperty("经度")
    private Double lng;
    @TableField(exist = false)
    @ApiModelProperty("纬度")
    private Double lat;
}
