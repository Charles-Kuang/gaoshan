package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.AuditEntity;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 行政区 gs_region
 *
 * @author ruoyi
 * @date 2022-11-14
 */
@ApiModel("行政区")
public class GsRegion extends AuditEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 行政区id
     */
    @ApiModelProperty("行政区id")
    private Long regionId;

    /**
     * 上级行政区划代码
     */
    @ApiModelProperty("上级行政区划代码")
    @NotNull
    @Excel(name = "上级行政区划代码")
    private Long parentCode;

    /**
     * 上级行政区划代码
     */
    @ApiModelProperty("上级行政区划代码")
    @Excel(name = "上级行政区划")
    private String parentName;

    /**
     * 祖级行政区划代码
     */
    @ApiModelProperty("祖级行政区划代码")
    @Excel(name = "祖宗")
    private String forefathers;

    /**
     * 所属公安机关
     */
    @ApiModelProperty("所属公安机关")
    @Excel(name = "所属公安机关")
    private Long police;

    /**
     * 行政区划代码
     */
    @ApiModelProperty("行政区划代码")
    @NotNull
    @Excel(name = "行政区划代码")
    private Long code;

    /**
     * 行政区简称
     */
    @ApiModelProperty("行政区简称")
    @NotNull
    @Excel(name = "行政区简称")
    private String name;

    /**
     * 行政区全称
     */
    @ApiModelProperty("行政区全称")
    @Excel(name = "行政区全称")
    private String fullName;

    /**
     * 拼音字母
     */
    @ApiModelProperty("拼音字母")
    @Excel(name = "拼音字母")
    private String firstLetter;

    /**
     * 中心经度
     */
    @ApiModelProperty("中心经度")
    @Excel(name = "中心经度")
    private String lng;

    /**
     * 中心纬度
     */
    @ApiModelProperty("中心纬度")
    @Excel(name = "中心纬度")
    private String lat;

    /**
     * 地理网格
     */
    @ApiModelProperty("地理网格")
    @Excel(name = "地理网格")
    private String geographicGrid;

    /**
     * 地理实体
     */
    @ApiModelProperty("地理实体")
    @Excel(name = "地理实体")
    private String geographicEnity;

    /** 数据状态 */
    @Excel(name = "数据状态")
    private String dataState;

    /** 数据状态 */
    @Excel(name = "数据状态")
    private String policeName;

    /**
     * 城乡分类
     */
    @Excel(name = "城乡分类")
    @ApiModelProperty("城乡分类")
    private String type;

    private int level;

    /** 作废标识 */
    @Excel(name = "作废标识")
    private String invalidFlag;

    /** 子部门 */
    private List<GsRegion> children = new ArrayList<GsRegion>();
    
    /**
     * 是否包含子项目
     */
    @ApiModelProperty("是否包含子项目")
    @Excel(name = "是否包含子项目")
    private Boolean hasChild;

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setParentCode(Long parentCode) {
        this.parentCode = parentCode;
    }

    public Long getParentCode() {
        return parentCode;
    }

    public void setPolice(Long police) {
        this.police = police;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getForefathers() {
        return forefathers;
    }

    public void setForefathers(String forefathers) {
        this.forefathers = forefathers;
    }

    public Long getPolice() {
        return police;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getCode() {
        return code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLng() {
        return lng;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLat() {
        return lat;
    }

    public void setGeographicGrid(String geographicGrid) {
        this.geographicGrid = geographicGrid;
    }

    public String getGeographicGrid() {
        return geographicGrid;
    }

    public void setGeographicEnity(String geographicEnity) {
        this.geographicEnity = geographicEnity;
    }

    public String getGeographicEnity() {
        return geographicEnity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<GsRegion> getChildren() {
        return children;
    }

    public void setChildren(List<GsRegion> children) {
        this.children = children;
    }

    public String getDataState() {
        return dataState;
    }

    public void setDataState(String dataState) {
        this.dataState = dataState;
    }

    public String getPoliceName() {
        return policeName;
    }

    public void setPoliceName(String policeName) {
        this.policeName = policeName;
    }

    public String getInvalidFlag() {
        return invalidFlag;
    }

    public void setInvalidFlag(String invalidFlag) {
        this.invalidFlag = invalidFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("regionId", getRegionId())
                .append("parentCode", getParentCode())
                .append("forefathers", getForefathers())
                .append("parentName", getParentName())
                .append("police", getPolice())
                .append("policeName", getPoliceName())
                .append("code", getCode())
                .append("name", getName())
                .append("fullName", getFullName())
                .append("firstLetter", getFirstLetter())
                .append("lng", getLng())
                .append("lat", getLat())
                .append("geographicGrid", getGeographicGrid())
                .append("geographicEnity", getGeographicEnity())
                .append("type", getType())
                .append("level", getLevel())
                .append("hasChild", getHasChild())
                .append("invalidFlag", getInvalidFlag())
                .append("dataState", getDataState())
                .append("remark", getRemark())
                .append("createUserId", getCreateUserId())
                .append("createUserPhone", getCreateUserPhone())
                .append("createTime", getCreateTime())
                .append("createUser", getCreateUser())
                .append("lastUpdateUser", getLastUpdateUser())
                .append("lastUpdateUserId", getLastUpdateUserId())
                .append("lastUpdateUserPhone", getLastUpdateUserPhone())
                .append("lastUpdateTime", getLastUpdateTime())
                .append("auditState", getAuditState())
                .append("auditUser", getAuditUser())
                .append("auditUserId", getAuditUserId())
                .append("auditUserPhone", getAuditUserPhone())
                .append("commitStatus", getCommitStatus())
                .toString();
    }
}
