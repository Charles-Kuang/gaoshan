package com.ruoyi.applet.domain;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.AuditEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;

/**
 * 标准详细地址对象 gs_region_detail
 * 
 * @author ruoyi
 * @date 2022-12-01
 */
public class GsRegionDetail extends AuditEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long addressId;

    /** 区域 */
    @Excel(name = "区域")
    private Long regionId;

    /** 详细完整地址，多级联动用-分割 */
    @Excel(name = "详细完整地址，多级联动用-分割")
    private String addressDetail;

    /** 经度 */
    @Excel(name = "经度")
    private BigDecimal lng;

    /** 纬度 */
    @Excel(name = "纬度")
    private BigDecimal lat;

    public void setAddressId(Long addressId) 
    {
        this.addressId = addressId;
    }

    public Long getAddressId() 
    {
        return addressId;
    }
    public void setRegionId(Long regionId) 
    {
        this.regionId = regionId;
    }

    public Long getRegionId() 
    {
        return regionId;
    }
    public void setAddressDetail(String addressDetail) 
    {
        this.addressDetail = addressDetail;
    }

    public String getAddressDetail() 
    {
        return addressDetail;
    }
    public void setLng(BigDecimal lng) 
    {
        this.lng = lng;
    }

    public BigDecimal getLng() 
    {
        return lng;
    }
    public void setLat(BigDecimal lat) 
    {
        this.lat = lat;
    }

    public BigDecimal getLat() 
    {
        return lat;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("addressId", getAddressId())
            .append("regionId", getRegionId())
            .append("addressDetail", getAddressDetail())
            .append("lng", getLng())
            .append("lat", getLat())
            .append("createTime", getCreateTime())
            .append("createUserId", getCreateUserId())
            .append("createUser", getCreateUser())
            .append("createUserPhone", getCreateUserPhone())
            .append("lastUpdateTime", getLastUpdateTime())
            .append("lastUpdateUser", getLastUpdateUser())
            .append("lastUpdateUserId", getLastUpdateUserId())
            .append("lastUpdateUserPhone", getLastUpdateUserPhone())
            .append("auditUser", getAuditUser())
            .append("auditUserId", getAuditUserId())
            .append("auditState", getAuditState())
            .append("auditUserPhone", getAuditUserPhone())
            .append("commitStatus", getCommitStatus())
            .toString();
    }
}
