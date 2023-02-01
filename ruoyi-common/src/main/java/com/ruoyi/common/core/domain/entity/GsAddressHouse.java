package com.ruoyi.common.core.domain.entity;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.AuditEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;

/**
 * addressHouse对象 gs_address_house
 * 
 * @author ruoyi
 * @date 2022-12-12
 */
public class GsAddressHouse extends AuditEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 二维地址编码 */
    private String qrCode;

    /** 全地址 */
    @Excel(name = "全地址")
    private String address;

    /** 地址门头照 */
    @Excel(name = "地址门头照")
    private String doorPhoto;

    /** 地址类型 */
    @Excel(name = "地址类型")
    private Long addressType;

    /** 纬度 */
    @Excel(name = "纬度")
    private BigDecimal latitude;

    /** 经度 */
    @Excel(name = "经度")
    private BigDecimal longitude;

    /** 行政区划 */
    @Excel(name = "行政区划")
    private String adcode;

    /** 城市 */
    @Excel(name = "城市")
    private String cityCode;

    /** 街道 */
    @Excel(name = "街道")
    private String townCode;

    /** 国家 */
    @Excel(name = "国家")
    private String nationCode;

    /** 楼/栋/村民组 */
    @Excel(name = "楼/栋/村民组")
    private String addressGroup;

    /** 门牌号/房间号 */
    @Excel(name = "门牌号/房间号")
    private String doorplate;

    /** 产权类型 */
    @Excel(name = "产权类型")
    private Long propertyType;

    /** 产权单位 */
    @Excel(name = "产权单位")
    private String propertyUnit;

    /** 产权人 */
    @Excel(name = "产权人")
    private String propertyPerson;

    /** 身份识别号 */
    @Excel(name = "身份识别号")
    private String identityNumber;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phoneNumber;

    /** 房屋类型 */
    @Excel(name = "房屋类型")
    private Long houseType;

    /** 房屋结构 */
    @Excel(name = "房屋结构")
    private String buildingStructure;

    /** 房屋面积 */
    @Excel(name = "房屋面积")
    private BigDecimal roomArea;

    /** 房屋间数 */
    @Excel(name = "房屋间数")
    private Long houseNumber;

    /** 房屋用途 */
    @Excel(name = "房屋用途")
    private String houseUse;

    /** 居住现状 */
    @Excel(name = "居住现状")
    private String livingStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setQrCode(String qrCode) 
    {
        this.qrCode = qrCode;
    }

    public String getQrCode() 
    {
        return qrCode;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setDoorPhoto(String doorPhoto) 
    {
        this.doorPhoto = doorPhoto;
    }

    public String getDoorPhoto() 
    {
        return doorPhoto;
    }
    public void setAddressType(Long addressType) 
    {
        this.addressType = addressType;
    }

    public Long getAddressType() 
    {
        return addressType;
    }
    public void setLatitude(BigDecimal latitude) 
    {
        this.latitude = latitude;
    }

    public BigDecimal getLatitude() 
    {
        return latitude;
    }
    public void setLongitude(BigDecimal longitude) 
    {
        this.longitude = longitude;
    }

    public BigDecimal getLongitude() 
    {
        return longitude;
    }
    public void setAdcode(String adcode) 
    {
        this.adcode = adcode;
    }

    public String getAdcode() 
    {
        return adcode;
    }
    public void setCityCode(String cityCode) 
    {
        this.cityCode = cityCode;
    }

    public String getCityCode() 
    {
        return cityCode;
    }
    public void setTownCode(String townCode) 
    {
        this.townCode = townCode;
    }

    public String getTownCode() 
    {
        return townCode;
    }
    public void setNationCode(String nationCode) 
    {
        this.nationCode = nationCode;
    }

    public String getNationCode() 
    {
        return nationCode;
    }
    public void setAddressGroup(String addressGroup) 
    {
        this.addressGroup = addressGroup;
    }

    public String getAddressGroup() 
    {
        return addressGroup;
    }
    public void setDoorplate(String doorplate) 
    {
        this.doorplate = doorplate;
    }

    public String getDoorplate() 
    {
        return doorplate;
    }
    public void setPropertyType(Long propertyType) 
    {
        this.propertyType = propertyType;
    }

    public Long getPropertyType() 
    {
        return propertyType;
    }
    public void setPropertyUnit(String propertyUnit) 
    {
        this.propertyUnit = propertyUnit;
    }

    public String getPropertyUnit() 
    {
        return propertyUnit;
    }
    public void setPropertyPerson(String propertyPerson) 
    {
        this.propertyPerson = propertyPerson;
    }

    public String getPropertyPerson() 
    {
        return propertyPerson;
    }
    public void setIdentityNumber(String identityNumber) 
    {
        this.identityNumber = identityNumber;
    }

    public String getIdentityNumber() 
    {
        return identityNumber;
    }
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
    }
    public void setHouseType(Long houseType) 
    {
        this.houseType = houseType;
    }

    public Long getHouseType() 
    {
        return houseType;
    }
    public void setBuildingStructure(String buildingStructure) 
    {
        this.buildingStructure = buildingStructure;
    }

    public String getBuildingStructure() 
    {
        return buildingStructure;
    }
    public void setRoomArea(BigDecimal roomArea) 
    {
        this.roomArea = roomArea;
    }

    public BigDecimal getRoomArea() 
    {
        return roomArea;
    }
    public void setHouseNumber(Long houseNumber) 
    {
        this.houseNumber = houseNumber;
    }

    public Long getHouseNumber() 
    {
        return houseNumber;
    }
    public void setHouseUse(String houseUse) 
    {
        this.houseUse = houseUse;
    }

    public String getHouseUse() 
    {
        return houseUse;
    }
    public void setLivingStatus(String livingStatus) 
    {
        this.livingStatus = livingStatus;
    }

    public String getLivingStatus() 
    {
        return livingStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("qrCode", getQrCode())
            .append("address", getAddress())
            .append("doorPhoto", getDoorPhoto())
            .append("addressType", getAddressType())
            .append("latitude", getLatitude())
            .append("longitude", getLongitude())
            .append("adcode", getAdcode())
            .append("cityCode", getCityCode())
            .append("townCode", getTownCode())
            .append("nationCode", getNationCode())
            .append("addressGroup", getAddressGroup())
            .append("doorplate", getDoorplate())
            .append("propertyType", getPropertyType())
            .append("propertyUnit", getPropertyUnit())
            .append("propertyPerson", getPropertyPerson())
            .append("identityNumber", getIdentityNumber())
            .append("phoneNumber", getPhoneNumber())
            .append("houseType", getHouseType())
            .append("buildingStructure", getBuildingStructure())
            .append("roomArea", getRoomArea())
            .append("houseNumber", getHouseNumber())
            .append("houseUse", getHouseUse())
            .append("livingStatus", getLivingStatus())
            .append("remark", getRemark())
            .append("createUserId", getCreateUserId())
            .append("createUser", getCreateUser())
            .append("createTime", getCreateTime())
            .append("createUserPhone", getCreateUserPhone())
            .append("lastUpdateUserId", getLastUpdateUserId())
            .append("lastUpdateUser", getLastUpdateUser())
            .append("lastUpdateUserPhone", getLastUpdateUserPhone())
            .append("lastUpdateTime", getLastUpdateTime())
            .append("auditUser", getAuditUser())
            .append("auditUserId", getAuditUserId())
            .append("auditState", getAuditState())
            .append("auditUserPhone", getAuditUserPhone())
            .append("commitStatus", getCommitStatus())
            .toString();
    }
}
