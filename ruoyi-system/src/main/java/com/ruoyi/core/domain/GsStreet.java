package com.ruoyi.core.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excels;
import com.ruoyi.common.core.domain.entity.SysDept;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 街路巷管理对象 gs_street
 * 
 * @author chenghaiqin
 * @date 2022-12-30
 */
public class GsStreet extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 街路巷(小区)编码 */
    private Long streetId;

    /** 街路巷 */
    @Excel(name = "街路巷")
    private String name;

    /** 所属行政区划编码 */
    @Excel(name = "所属行政区划编码")
    private Long regionCode;

    /** 所属行政区划名称 */
    @Excel(name = "所属行政区划名称")
    private String region;

    /** 所属乡镇街道编码 */
    @Excel(name = "所属乡镇街道编码")
    private Long townsCode;

    /** 所属乡镇街道名称 */
    @Excel(name = "所属乡镇街道名称")
    private String towns;

    /** 所属居(村)委会编码 */
    @Excel(name = "所属居(村)委会编码")
    private Long committeeCode;

    /** 所属居(村)委会名称 */
    @Excel(name = "所属居(村)委会名称")
    private String committee;

    /** 所属公安机关 */
    @Excel(name = "所属公安机关")
    private Long policeId;

    /** 拼音首字母 */
    @Excel(name = "拼音首字母")
    private String firstLetter;

    /** 别名 */
    @Excel(name = "别名")
    private String alias;

    /** 中心经度 */
    @Excel(name = "中心经度")
    private String lng;

    /** 中心纬度 */
    @Excel(name = "中心纬度")
    private String lat;

    /** 地址围栏 */
    @Excel(name = "地址围栏")
    private String addressFence;

    /** 数据来源 */
    @Excel(name = "数据来源")
    private String dataSources;

    /** 作废标识 */
    @Excel(name = "作废标识")
    private String invalidFlag;

    /** 是否入库 */
    @Excel(name = "是否入库")
    private String inStorage;

    /** 入库人 */
    @Excel(name = "入库人")
    private String storageUser;

    /** 入库人id */
    @Excel(name = "入库人id")
    private Long storageUserId;

    /** 入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date storageTime;

    /** 录入单位 */
    @Excel(name = "录入单位")
    private String createUnit;

    /** 创建用户id */
    @Excel(name = "创建用户id")
    private Long createUserId;

    /** 创建用户名称 */
    @Excel(name = "创建用户名称")
    private String createUser;

    /** 创建用户联系电话 */
    @Excel(name = "创建用户联系电话")
    private String createUserPhone;

    /** 最后更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date lastUpdateTime;

    /** 最后更新人 */
    @Excel(name = "最后更新人")
    private String lastUpdateUser;

    /** 最后更新人id */
    @Excel(name = "最后更新人id")
    private Long lastUpdateUserId;

    /** 最后更新人联系电话 */
    @Excel(name = "最后更新人联系电话")
    private String lastUpdateUserPhone;

    /** 审核人 */
    @Excel(name = "审核人")
    private String auditUser;

    /** 审核人id */
    @Excel(name = "审核人id")
    private Long auditUserId;

    /** 审核状态 */
    @Excel(name = "审核状态")
    private Long auditState;

    /** 审核人电话 */
    @Excel(name = "审核人电话")
    private String auditUserPhone;

    /** 部门对象 */
    @Excels({
            @Excel(name = "部门名称", targetAttr = "deptName", type = Excel.Type.EXPORT),
            @Excel(name = "部门负责人", targetAttr = "leader", type = Excel.Type.EXPORT)
    })
    private SysDept dept;

    public void setStreetId(Long streetId) 
    {
        this.streetId = streetId;
    }

    public Long getStreetId() 
    {
        return streetId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setRegionCode(Long regionCode) 
    {
        this.regionCode = regionCode;
    }

    public Long getRegionCode() 
    {
        return regionCode;
    }
    public void setRegion(String region) 
    {
        this.region = region;
    }

    public String getRegion() 
    {
        return region;
    }
    public void setTownsCode(Long townsCode) 
    {
        this.townsCode = townsCode;
    }

    public Long getTownsCode() 
    {
        return townsCode;
    }
    public void setTowns(String towns) 
    {
        this.towns = towns;
    }

    public String getTowns() 
    {
        return towns;
    }
    public void setCommitteeCode(Long committeeCode) 
    {
        this.committeeCode = committeeCode;
    }

    public Long getCommitteeCode() 
    {
        return committeeCode;
    }
    public void setCommittee(String committee) 
    {
        this.committee = committee;
    }

    public String getCommittee() 
    {
        return committee;
    }
    public void setPoliceId(Long policeId) 
    {
        this.policeId = policeId;
    }

    public Long getPoliceId() 
    {
        return policeId;
    }
    public void setFirstLetter(String firstLetter) 
    {
        this.firstLetter = firstLetter;
    }

    public String getFirstLetter() 
    {
        return firstLetter;
    }
    public void setAlias(String alias) 
    {
        this.alias = alias;
    }

    public String getAlias() 
    {
        return alias;
    }
    public void setLng(String lng) 
    {
        this.lng = lng;
    }

    public String getLng() 
    {
        return lng;
    }
    public void setLat(String lat) 
    {
        this.lat = lat;
    }

    public String getLat() 
    {
        return lat;
    }
    public void setAddressFence(String addressFence) 
    {
        this.addressFence = addressFence;
    }

    public String getAddressFence() 
    {
        return addressFence;
    }
    public void setDataSources(String dataSources) 
    {
        this.dataSources = dataSources;
    }

    public String getDataSources() 
    {
        return dataSources;
    }
    public void setInvalidFlag(String invalidFlag) 
    {
        this.invalidFlag = invalidFlag;
    }

    public String getInvalidFlag() 
    {
        return invalidFlag;
    }
    public void setInStorage(String inStorage) 
    {
        this.inStorage = inStorage;
    }

    public String getInStorage() 
    {
        return inStorage;
    }
    public void setStorageUser(String storageUser) 
    {
        this.storageUser = storageUser;
    }

    public String getStorageUser() 
    {
        return storageUser;
    }
    public void setStorageUserId(Long storageUserId) 
    {
        this.storageUserId = storageUserId;
    }

    public Long getStorageUserId() 
    {
        return storageUserId;
    }
    public void setStorageTime(Date storageTime) 
    {
        this.storageTime = storageTime;
    }

    public Date getStorageTime() 
    {
        return storageTime;
    }
    public void setCreateUnit(String createUnit) 
    {
        this.createUnit = createUnit;
    }

    public String getCreateUnit() 
    {
        return createUnit;
    }
    public void setCreateUserId(Long createUserId) 
    {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() 
    {
        return createUserId;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }
    public void setCreateUserPhone(String createUserPhone) 
    {
        this.createUserPhone = createUserPhone;
    }

    public String getCreateUserPhone() 
    {
        return createUserPhone;
    }
    public void setLastUpdateTime(Date lastUpdateTime) 
    {
        this.lastUpdateTime = lastUpdateTime;
    }

    public Date getLastUpdateTime() 
    {
        return lastUpdateTime;
    }
    public void setLastUpdateUser(String lastUpdateUser) 
    {
        this.lastUpdateUser = lastUpdateUser;
    }

    public String getLastUpdateUser() 
    {
        return lastUpdateUser;
    }
    public void setLastUpdateUserId(Long lastUpdateUserId) 
    {
        this.lastUpdateUserId = lastUpdateUserId;
    }

    public Long getLastUpdateUserId() 
    {
        return lastUpdateUserId;
    }
    public void setLastUpdateUserPhone(String lastUpdateUserPhone) 
    {
        this.lastUpdateUserPhone = lastUpdateUserPhone;
    }

    public String getLastUpdateUserPhone() 
    {
        return lastUpdateUserPhone;
    }
    public void setAuditUser(String auditUser) 
    {
        this.auditUser = auditUser;
    }

    public String getAuditUser() 
    {
        return auditUser;
    }
    public void setAuditUserId(Long auditUserId) 
    {
        this.auditUserId = auditUserId;
    }

    public Long getAuditUserId() 
    {
        return auditUserId;
    }
    public void setAuditState(Long auditState) 
    {
        this.auditState = auditState;
    }

    public Long getAuditState() 
    {
        return auditState;
    }
    public void setAuditUserPhone(String auditUserPhone) 
    {
        this.auditUserPhone = auditUserPhone;
    }

    public String getAuditUserPhone() 
    {
        return auditUserPhone;
    }

    public SysDept getDept()
    {
        return dept;
    }

    public void setDept(SysDept dept)
    {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("streetId", getStreetId())
            .append("name", getName())
            .append("regionCode", getRegionCode())
            .append("region", getRegion())
            .append("townsCode", getTownsCode())
            .append("towns", getTowns())
            .append("committeeCode", getCommitteeCode())
            .append("committee", getCommittee())
            .append("policeId", getPoliceId())
            .append("firstLetter", getFirstLetter())
            .append("alias", getAlias())
            .append("lng", getLng())
            .append("lat", getLat())
            .append("addressFence", getAddressFence())
            .append("dataSources", getDataSources())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("invalidFlag", getInvalidFlag())
            .append("inStorage", getInStorage())
            .append("storageUser", getStorageUser())
            .append("storageUserId", getStorageUserId())
            .append("storageTime", getStorageTime())
            .append("createUnit", getCreateUnit())
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
            .append("dept", getDept())
            .toString();
    }
}
