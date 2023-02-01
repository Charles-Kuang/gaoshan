package com.gaoshan.modules.wechat.region.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoshan.modules.wechat.region.domain.Region;
import com.gaoshan.modules.wechat.region.service.RegionService;
import com.gaoshan.util.Result;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Api(tags = "行政区域查询")
@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;
    @ApiOperation("获取区域信息")
    @GetMapping("/regionData/{parentCode}")
    public Result regionData(@PathVariable Integer parentCode,Integer page,Integer pageSize,String keyword){
        QueryWrapper<Region> queryWrapper=new QueryWrapper<Region>();
        queryWrapper.select("parent_code","code","name","region_id");
        if(null!=parentCode){
            queryWrapper.eq("parent_code",parentCode);
        }else{
            return Result.ERROR("区域父级编码未传入！",404);
        }
        if(StringUtils.isNotEmpty(keyword)){
            queryWrapper.like("name",keyword);
        }
        if(null!=page&&null!=pageSize){
            Page pages=new Page(page,pageSize);
            IPage<Region>  pageData=regionService.page(pages,queryWrapper);
            return Result.SUCCESS(pageData);
        }
        List<Region> regionList=regionService.list(queryWrapper);
        return Result.SUCCESS(regionList);
    }
}
