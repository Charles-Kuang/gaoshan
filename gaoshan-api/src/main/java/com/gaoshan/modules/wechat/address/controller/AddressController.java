package com.gaoshan.modules.wechat.address.controller;

import com.gaoshan.enumeration.HTTP_STATUS_CODE_ENUM;
import com.gaoshan.modules.wechat.address.service.AddressService;
import com.gaoshan.util.Result;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 标准地址统一管理,除行政区划已固定的以外的地址
 */
@Api(tags="详细地址信息查询")
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    AddressService addressService;
    @ApiOperation("根据关键字查询分页的详细地址")
    @GetMapping("/searchAddress")
    public Result addressSearch(String keyword, Integer page, Integer pageSize,Integer regionId){
        if(StringUtils.isEmpty(keyword)){
            return Result.ERROR(HTTP_STATUS_CODE_ENUM.NOT_FIND_STATUS);
        }
        if(null==page||null==pageSize){
            return Result.ERROR("分页查询条件不能为空！",404);
        }
        return addressService.addressSearch(keyword,page,pageSize,regionId);
    }
}
