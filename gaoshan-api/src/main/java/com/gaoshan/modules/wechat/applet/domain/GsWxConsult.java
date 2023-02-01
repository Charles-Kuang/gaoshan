package com.gaoshan.modules.wechat.applet.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 服务咨询对象 gs_wx_consult
 * 
 * @author ruoyi
 * @date 2022-11-18
 */
@ApiModel("服务咨询")
public class GsWxConsult
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 主题 */
    @Excel(name = "主题")
    @ApiModelProperty("主题")
    private String theme;

    /** 内容 */
    @Excel(name = "内容")
    @ApiModelProperty("内容")
    private String matter;

    /** 标语 */
    @Excel(name = "标语")
    @ApiModelProperty("标语")
    private String slogan;

    /** 缩略图 */
    @Excel(name = "缩略图")
    @ApiModelProperty("缩略图")
    private String thumbnail;

    /** 咨询提示 */
    @Excel(name = "咨询提示")
    @ApiModelProperty("咨询提示")
    private String hint;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTheme(String theme) 
    {
        this.theme = theme;
    }

    public String getTheme() 
    {
        return theme;
    }
    public void setMatter(String matter) 
    {
        this.matter = matter;
    }

    public String getMatter() 
    {
        return matter;
    }
    public void setSlogan(String slogan) 
    {
        this.slogan = slogan;
    }

    public String getSlogan() 
    {
        return slogan;
    }
    public void setThumbnail(String thumbnail) 
    {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() 
    {
        return thumbnail;
    }
    public void setHint(String hint) 
    {
        this.hint = hint;
    }

    public String getHint() 
    {
        return hint;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("theme", getTheme())
            .append("matter", getMatter())
            .append("slogan", getSlogan())
            .append("thumbnail", getThumbnail())
            .append("hint", getHint())
            .toString();
    }
}
