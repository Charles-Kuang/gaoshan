package com.gaoshan.modules.wechat.applet.controller;

import com.gaoshan.modules.wechat.applet.domain.GsWxConsult;
import com.gaoshan.modules.wechat.applet.service.IGsWxConsultService;
import com.gaoshan.util.Result;
import com.ruoyi.common.core.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 服务咨询Controller
 * 
 * @author ruoyi
 * @date 2022-11-18
 */
@Api(tags = "服务咨询")
@RestController
@RequestMapping("/applet/wxConsult")
public class GsWxConsultController extends BaseController
{
    @Autowired
    private IGsWxConsultService gsWxConsultService;

    /**
     * 查询服务咨询列表
     */
    @ApiOperation("查询服务咨询列表")
    @GetMapping("/list")
    public Result list()
    {
        List<GsWxConsult> list = gsWxConsultService.list();
        return Result.SUCCESS(list);
    }

    /**
     * 获取服务咨询详细信息
     */
    @ApiOperation("获取服务咨询详细信息")
    @GetMapping(value = "/{id}")
    public Result getInfo(@PathVariable("id") Long id)
    {
        return Result.SUCCESS(gsWxConsultService.getById(id));
    }

}
