package com.ruoyi.applet.domain.vo;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class CityRegionVo {

    /**
     * 行政区划代码
     */
    @ApiModelProperty("行政区划代码")
    @NotNull
    @Excel(name = "行政区划代码")
    private Long cityCode;

    /**
     * 行政区简称
     */
    @ApiModelProperty("行政区简称")
    @NotNull
    @Excel(name = "行政区简称")
    private String cityName;

    /**
     * 是否包含子项目
     */
    @ApiModelProperty("是否包含子项目")
    @Excel(name = "是否包含子项目")
    private Boolean hasChild;

    /** 子部门 */
    private List<CityRegionVo> children = new ArrayList<CityRegionVo>();

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public Boolean getHasChild() {
        return hasChild;
    }

    public void setHasChild(Boolean hasChild) {
        this.hasChild = hasChild;
    }

    public List<CityRegionVo> getChildren() {
        return children;
    }

    public void setChildren(List<CityRegionVo> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("cityCode", getCityCode())
                .append("cityName", getCityName())
                .append("hasChild", getHasChild())
                .append("cityName", getChildren())
                .toString();
    }
}
