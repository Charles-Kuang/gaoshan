package com.gaoshan.modules.wechat.applet.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 新闻资讯对象 gs_article
 * 
 * @author ruoyi
 * @date 2022-11-01
 */
@Data
@ApiModel("新闻资讯")
@TableName("gs_article")
public class GsArticle {

    /** ID */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("新闻资讯ID")
    private Long id;

    /** 文章标题 */
    @ApiModelProperty("文章标题")
    private String title;

    /** 缩略图 */
    @ApiModelProperty("缩略图")
    private String imagePath;
    @ApiModelProperty("简介")
    private String brief;
    /** 文章内容 */
    @ApiModelProperty("文章内容")
    private String content;

    /** 是否允许评论 */
    @ApiModelProperty("是否允许评论")
    private Integer comment;

    /** 浏览量 */
    @ApiModelProperty("浏览量")
    private Integer clicks;

    /** 权重 */
    @ApiModelProperty("权重")
    private Integer weight;

    /** 是否发布 */
    @ApiModelProperty("文章标题")
    private Integer status;

    /** 发布时间 */
    @ApiModelProperty("发布时间")
    private Date publishTime;

}
