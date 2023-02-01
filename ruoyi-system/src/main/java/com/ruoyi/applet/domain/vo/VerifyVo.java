package com.ruoyi.applet.domain.vo;

import com.ruoyi.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

public class VerifyVo {

    /**
     * 行政区划代码
     */
    @NotNull
    @Excel(name = "行政区划代码")
    private Long region;

    /**
     * 行政区划代码
     */
    @NotNull
    @Excel(name = "行政区划代码")
    private Long person;

    /**
     * 行政区划代码
     */
    @NotNull
    @Excel(name = "行政区划代码")
    private Long house;

    /**
     * 行政区划代码
     */
    @NotNull
    @Excel(name = "行政区划代码")
    private Long unit;

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    public Long getPerson() {
        return person;
    }

    public void setPerson(Long person) {
        this.person = person;
    }

    public Long getHouse() {
        return house;
    }

    public void setHouse(Long house) {
        this.house = house;
    }

    public Long getUnit() {
        return unit;
    }

    public void setUnit(Long unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("region", getRegion())
                .append("person", getPerson())
                .append("house", getHouse())
                .append("unit", getUnit())
                .toString();
    }
}
