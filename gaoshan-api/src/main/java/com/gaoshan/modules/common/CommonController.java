package com.gaoshan.modules.common;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaoshan.aspect.auth.LoginAuth;
import com.gaoshan.enumeration.HTTP_STATUS_CODE_ENUM;
import com.gaoshan.util.Result;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

public abstract class CommonController<T> {
    protected abstract IService<T> getCoreService();
    @ApiOperation("分页数据,查询参数where")
    @GetMapping("/listPage")
    @ResponseBody
    public Result listPage(Integer page,Integer pageSize,String where){
        Page<T> pageParams=new Page<T>(page,pageSize);
        QueryWrapper<T> queryWrapper=new QueryWrapper<T>();
        if(StringUtils.isNotEmpty(where)){
            if(where.contains("&")){
                String[] whereDatas=where.split("&");
                for (String whereStr:whereDatas) {
                    String[] whereParam=whereStr.split("=");
                    queryWrapper.eq(whereParam[0],whereParam[1]);
                }
            }else{
                String[] whereParam=where.split("=");
                queryWrapper.eq(whereParam[0],whereParam[1]);
            }
        }

        IPage<T> pageData=getCoreService().page(pageParams,queryWrapper);
        return Result.SUCCESS(pageData);
    }
    @GetMapping("/list")
    @ResponseBody
    public Result list(String where){
        QueryWrapper<T>  queryWrapper=new QueryWrapper<T>();
        if(StringUtils.isNotEmpty(where)){
            if(where.contains("&")){
                String[] whereDatas=where.split("&");
                for (String whereStr:whereDatas) {
                    String[] whereParam=whereStr.split("=");
                    queryWrapper.eq(whereParam[0],whereParam[1]);
                }
            }else{
                String[] whereParam=where.split("=");
                queryWrapper.eq(whereParam[0],whereParam[1]);
            }
        }
        List<T> dataList=getCoreService().list(queryWrapper);
        return Result.SUCCESS(dataList);
    }
//    @LoginAuth(remark = "数据增加操作")
    @PostMapping("/addData")
    @ResponseBody
    public Result addData(@RequestBody T t){
        getCoreService().save(t);
        return Result.SUCCESS(t,HTTP_STATUS_CODE_ENUM.SUCCESS_STATUS.getMessage());
    }
//    @LoginAuth(remark = "数据更新操作")
    @PutMapping("/updateData")
    @ResponseBody
    public Result updateData(@RequestBody T t,String where){
        QueryWrapper queryWrapper=new QueryWrapper();
        if(StringUtils.isNotEmpty(where)){
            if(where.contains("&")){
                String[] whereDatas=where.split("&");
                for (String whereStr:whereDatas) {
                    String[] whereParam=whereStr.split("=");
                    queryWrapper.eq(whereParam[0],whereParam[1]);
                }
            }else{
                String[] whereParam=where.split("=");
                queryWrapper.eq(whereParam[0],whereParam[1]);
            }
            getCoreService().update(t,queryWrapper);
        }else{
            getCoreService().updateById(t);
        }

        return Result.SUCCESS(HTTP_STATUS_CODE_ENUM.SUCCESS_STATUS);
    }
    @GetMapping("/getDataById/{id}")
    @ResponseBody
    public Result getDataById(@PathVariable Serializable id, String where){
        T t;
        QueryWrapper<T> queryWrapper=new QueryWrapper<T>();
        if(StringUtils.isNotEmpty(where)){
            if(where.contains("&")){
                String[] whereDatas=where.split("&");
                for (String whereStr:whereDatas) {
                    String[] whereParam=whereStr.split("=");
                    queryWrapper.eq(whereParam[0],whereParam[1]);
                }
            }else{
                String[] whereParam=where.split("=");
                queryWrapper.eq(whereParam[0],whereParam[1]);
            }

            t=getCoreService().getOne(queryWrapper);
        }else{
            t=getCoreService().getById(id);
        }
        return Result.SUCCESS(t);
    }
}
