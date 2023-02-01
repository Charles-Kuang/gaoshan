package com.gaoshan.modules.wechat.address.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gaoshan.modules.wechat.common.domain.Common;
import lombok.Data;

/**
 * 详细地址到门牌的，除省市县 固定的联动外
 */
@Data
@TableName("gs_region_detail")
public class Address extends Common{
    @TableId(value = "address_id", type = IdType.AUTO)
    private Integer addressId;
    private Integer regionId;
    private String addressDetail;
    private Double lng;
    private Double lat;
}
