package com.gaoshan.modules.wechat.applet.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoshan.modules.wechat.applet.domain.GsArticle;
import com.gaoshan.modules.wechat.applet.service.IGsArticleService;
import com.gaoshan.util.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 新闻资讯Controller
 * 
 * @author ruoyi
 * @date 2022-11-01
 */
@Api(tags = "新闻资讯")
@RestController
@RequestMapping("/applet/wxArticle")
public class GsArticleController
{
    @Autowired
    private IGsArticleService gsArticleService;

    /**
     * 查询新闻资讯列表
     */
    @ApiOperation("查询新闻资讯列表")
    @GetMapping("/list")
    public Result list() {
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.select("id","title","brief","image_path","comment","clicks","weight","status","publish_time","remark");
        List<GsArticle> list = gsArticleService.list(queryWrapper);
        return Result.SUCCESS(list);
    }
    @ApiOperation("查询新闻资讯分页列表")
    @GetMapping("/listPage")
    public Result listPage(Integer page,Integer pageSize) {
        Page pageWhere=new Page<>(page.longValue(),pageSize.longValue());
        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.select("id","title","brief","image_path","comment","clicks","weight","status","publish_time","remark");
        IPage pageData= gsArticleService.page(pageWhere,queryWrapper);
        return Result.SUCCESS(pageData);
    }
    /**
     * 获取新闻资讯详细信息
     */
    @ApiOperation("获取新闻资讯详细信息")
    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable("id") Long id) {
        GsArticle gsArticle = gsArticleService.getById(id);
        return Result.SUCCESS(gsArticle);
    }

}
