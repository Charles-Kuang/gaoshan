package com.ruoyi.applet.domain;

import java.math.BigDecimal;

import com.ruoyi.common.core.domain.AuditEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;

/**
 * 实有房屋对象 gs_house
 * 
 * @author ruoyi
 * @date 2022-11-26
 */
public class GsHouse extends AuditEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 房屋id
     */
    private Long houseId;

    /**
     * 地址id
     */
    @Excel(name = "地址id")
    private Long addressId;

    /**
     * 详细完整地址
     */
    @Excel(name = "详细完整地址")
    private String addressDetail;

    /**
     * 房间数
     */
    @Excel(name = "房间数")
    private Long houseNumber;

    /** 居住人数 */
    @Excel(name = "居住人数")
    private Long dwellPersonNumber;

    /**
     * 其他名称/别名
     */
    @Excel(name = "其他名称/别名")
    private String anotherName;

    /** 产权人/负责人 */
    @Excel(name = "产权人/负责人")
    private String propertyPerson;

    /** 产权人/负责人身份证号 */
    @Excel(name = "产权人/负责人身份证号")
    private String propertyPersonIdentityNumber;

    /** 产权人居住地址 */
    @Excel(name = "产权人居住地址")
    private String propertyPersonDwellAddress;

    /** 产权人联系电话 */
    @Excel(name = "产权人联系电话")
    private String propertyPersonPhone;

    /** 房屋类型 */
    @Excel(name = "房屋类型")
    private Long houseType;

    /** 关注级别 */
    @Excel(name = "关注级别")
    private Long attentionLevel;

    /** 房屋面积 */
    @Excel(name = "房屋面积")
    private BigDecimal houseArea;

    /** 房屋用途 */
    @Excel(name = "房屋用途")
    private Long houseUse;

    /** 门头照 */
    @Excel(name = "门头照")
    private String doorImage;

    /**
     * 出租详址
     */
    @Excel(name = "出租详址")
    private String detailAddress;

    /**
     * 数据状态
     */
    @Excel(name = "数据状态")
    private String dataState;

    public void setHouseId(Long houseId) {
        this.houseId = houseId;
    }

    public Long getHouseId() {
        return houseId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setHouseNumber(Long houseNumber) {
        this.houseNumber = houseNumber;
    }

    public Long getHouseNumber() {
        return houseNumber;
    }

    public void setAnotherName(String anotherName) {
        this.anotherName = anotherName;
    }

    public String getAnotherName() {
        return anotherName;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public String getDataState() {
        return dataState;
    }

    public void setDataState(String dataState) {
        this.dataState = dataState;
    }

    public Long getDwellPersonNumber() {
        return dwellPersonNumber;
    }

    public void setDwellPersonNumber(Long dwellPersonNumber) {
        this.dwellPersonNumber = dwellPersonNumber;
    }

    public String getPropertyPerson() {
        return propertyPerson;
    }

    public void setPropertyPerson(String propertyPerson) {
        this.propertyPerson = propertyPerson;
    }

    public String getPropertyPersonIdentityNumber() {
        return propertyPersonIdentityNumber;
    }

    public void setPropertyPersonIdentityNumber(String propertyPersonIdentityNumber) {
        this.propertyPersonIdentityNumber = propertyPersonIdentityNumber;
    }

    public String getPropertyPersonDwellAddress() {
        return propertyPersonDwellAddress;
    }

    public void setPropertyPersonDwellAddress(String propertyPersonDwellAddress) {
        this.propertyPersonDwellAddress = propertyPersonDwellAddress;
    }

    public String getPropertyPersonPhone() {
        return propertyPersonPhone;
    }

    public void setPropertyPersonPhone(String propertyPersonPhone) {
        this.propertyPersonPhone = propertyPersonPhone;
    }

    public Long getHouseType() {
        return houseType;
    }

    public void setHouseType(Long houseType) {
        this.houseType = houseType;
    }

    public Long getAttentionLevel() {
        return attentionLevel;
    }

    public void setAttentionLevel(Long attentionLevel) {
        this.attentionLevel = attentionLevel;
    }

    public BigDecimal getHouseArea() {
        return houseArea;
    }

    public void setHouseArea(BigDecimal houseArea) {
        this.houseArea = houseArea;
    }

    public Long getHouseUse() {
        return houseUse;
    }

    public void setHouseUse(Long houseUse) {
        this.houseUse = houseUse;
    }

    public String getDoorImage() {
        return doorImage;
    }

    public void setDoorImage(String doorImage) {
        this.doorImage = doorImage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("houseId", getHouseId())
                .append("addressId", getAddressId())
                .append("detailAddress", getDetailAddress())
                .append("addressDetail", getAddressDetail())
                .append("houseNumber", getHouseNumber())
                .append("dwellPersonNumber", getDwellPersonNumber())
                .append("anotherName", getAnotherName())
                .append("propertyPerson", getPropertyPerson())
                .append("propertyPersonIdentityNumber", getPropertyPersonIdentityNumber())
                .append("propertyPersonDwellAddress", getPropertyPersonDwellAddress())
                .append("propertyPersonPhone", getPropertyPersonPhone())
                .append("houseType", getHouseType())
                .append("attentionLevel", getAttentionLevel())
                .append("houseArea", getHouseArea())
                .append("houseUse", getHouseUse())
                .append("doorImage", getDoorImage())
                .append("remark", getRemark())
                .append("auditUserPhone", getAuditUserPhone())
                .append("auditUser", getAuditUser())
                .append("auditState", getAuditState())
                .append("auditUserId", getAuditUserId())
                .append("dataState", getDataState())
                .append("createUserPhone", getCreateUserPhone())
                .append("createUser", getCreateUser())
                .append("createTime", getCreateTime())
                .append("lastUpdateUser", getLastUpdateUser())
                .append("lastUpdateTime", getLastUpdateTime())
                .append("lastUpdateUserPhone", getLastUpdateUserPhone())
                .append("lastUpdateUserId", getLastUpdateUserId())
                .append("createUserId", getCreateUserId())
                .append("commitStatus", getCommitStatus())
                .toString();
    }
}
