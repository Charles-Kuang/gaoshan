package com.ruoyi.applet.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 新闻咨询对象 gs_article
 *
 * @author ruoyi
 * @date 2022-12-07
 */
public class GsArticle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long id;

    /** 文章标题 */
    @Excel(name = "文章标题")
    private String title;

    /** 缩略图 */
    @Excel(name = "缩略图")
    private String imagePath;

    /** 简介 */
    @Excel(name = "简介")
    private String brief;

    /** 文章内容 */
    @Excel(name = "文章内容")
    private String content;

    /** 是否允许评论 */
    @Excel(name = "是否允许评论")
    private String comment;

    /** 浏览量 */
    @Excel(name = "浏览量")
    private Long clicks;

    /** 权重 */
    @Excel(name = "权重")
    private Long weight;

    /** 是否发布 */
    @Excel(name = "是否发布")
    private String status;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    public String getImagePath()
    {
        return imagePath;
    }
    public void setBrief(String brief)
    {
        this.brief = brief;
    }

    public String getBrief()
    {
        return brief;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getComment()
    {
        return comment;
    }
    public void setClicks(Long clicks)
    {
        this.clicks = clicks;
    }

    public Long getClicks()
    {
        return clicks;
    }
    public void setWeight(Long weight)
    {
        this.weight = weight;
    }

    public Long getWeight()
    {
        return weight;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime()
    {
        return publishTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("title", getTitle())
                .append("imagePath", getImagePath())
                .append("brief", getBrief())
                .append("content", getContent())
                .append("comment", getComment())
                .append("clicks", getClicks())
                .append("weight", getWeight())
                .append("status", getStatus())
                .append("publishTime", getPublishTime())
                .append("remark", getRemark())
                .toString();
    }
}
