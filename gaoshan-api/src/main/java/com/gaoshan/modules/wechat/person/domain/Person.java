package com.gaoshan.modules.wechat.person.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gaoshan.modules.wechat.common.domain.Common;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("实有人口实体类")
@Data
@TableName("gs_person")
public class Person extends Common {
    @TableId(value = "person_id", type = IdType.AUTO)
    private Integer personId;
    @ApiModelProperty("通过查询获取到的地址id，无则不传入")
    private Integer addressId;
    @ApiModelProperty("详细完整地址")
    private String addressDetail;
    @ApiModelProperty("身份证类型")
    private Integer identityType;
    @ApiModelProperty("身份证号码")
    private String identityNumber;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("曾用名")
    private String usedName;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("人员头像")
    private String personImg;
    @ApiModelProperty("英文名")
    private String englishName;
    @ApiModelProperty("人员类型")
    private String personType;
    @ApiModelProperty("民族")
    private String nation;
    @ApiModelProperty("出生日期")
    private String birthday;
    @ApiModelProperty("户籍地址id")
    private String nativePlaceAddressId;
    @ApiModelProperty("户籍完整地址")
    private String nativePlaceAddressDetail;
    @ApiModelProperty("职业")
    private String job;
    @ApiModelProperty("文化程度")
    private String education;
    @ApiModelProperty(value = "现单位id",hidden = true)
    private String nowUnitId;
    @ApiModelProperty("婚姻关系")
    private String marital;
    @ApiModelProperty("电话号码")
    private String phoneNumber;
    @ApiModelProperty("暂住事由")
    private String reason;
    @ApiModelProperty("就业情况")
    private String employment;
    @ApiModelProperty("数据状态")
    private String dataState;
    @ApiModelProperty("备注")
    private String remark;
    @TableField(exist = false)
    @ApiModelProperty("经度")
    private Double lng;
    @TableField(exist = false)
    @ApiModelProperty("纬度")
    private Double lat;
    @TableField(exist = false)
    @ApiModelProperty("区域/居委会id")
    private Integer regionId;
    @ApiModelProperty("身份证反面照")
    private String identityReverse;
    @ApiModelProperty("身份证正面照")
    private String identityFront;
    @ApiModelProperty("年龄")
    private Integer age;
    @ApiModelProperty("关注级别")
    private Integer attentionLevel;
    @ApiModelProperty("名人类型")
    private Integer celebrity;
    @ApiModelProperty("是否帮扶救助对象")
    private Integer supportObject;
    @ApiModelProperty("特长")
    private String specialty;
    @ApiModelProperty("出行工具")
    private String vehicle;

}
