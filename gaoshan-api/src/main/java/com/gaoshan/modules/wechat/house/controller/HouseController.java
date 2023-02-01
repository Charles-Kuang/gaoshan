package com.gaoshan.modules.wechat.house.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoshan.aspect.auth.LoginAuth;
import com.gaoshan.modules.wechat.address.domain.Address;
import com.gaoshan.modules.wechat.address.service.AddressService;
import com.gaoshan.modules.wechat.enumeration.OPERATION_TYPE_ENUM;
import com.gaoshan.modules.wechat.house.domain.House;
import com.gaoshan.modules.wechat.house.service.HouseService;
import com.gaoshan.modules.wechat.operation.record.domain.OperationRecord;
import com.gaoshan.modules.wechat.operation.record.service.OperationRecordService;
import com.gaoshan.modules.wechat.user.domain.User;
import com.gaoshan.util.Result;
import com.gaoshan.util.TokenUtil;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @author luoqy
 */
@Api(tags="实有房屋信息相关接口")
@RestController
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private HouseService houseService;
    @Autowired
    private OperationRecordService operationRecordService;
    @Autowired
    private AddressService addressService;
    @ApiOperation("增加实有房屋信息")
    @PostMapping("/addHouse")
    public Result enteringHouse(@RequestBody House house){
        /***************保存地址信息*********************/
        if(null==house.getAddressId()){
            //已存在的地址信息
            Address address=new Address();
            address.setAddressDetail(house.getAddressDetail());
            address.setCreateTime(new Date());
            address.setRegionId(house.getRegionId());
            address.setLng(house.getLng());
            address.setLat(house.getLat());
            addressService.save(address);
            //设置保存后的地址id
            house.setAddressId(address.getAddressId());
        }
        /***************保存地址信息结束*********************/
        User user=TokenUtil.getTokenUser();

        house.setCreateTime(new Date());
        house.setCreateUser(StringUtils.isNotEmpty(user.getRealName())?user.getRealName():user.getNickName());
        house.setCreateUserId(user.getUserId());
        house.setCreateUserPhone(user.getPhone());
        houseService.save(house);
        return Result.SUCCESS(house);
    }
    @LoginAuth(remark = "审核实有房屋信息")
    @ApiOperation("完善更新实有房屋信息")
    @PutMapping("/updateHouse")
    public Result perfectHouse(@RequestBody House house){
        OperationRecord operationRecord=new OperationRecord();
        House prevDataHouse=houseService.getById(house.getHouseId());
        operationRecord.setPrevData(JSON.toJSONString(prevDataHouse));
        houseService.updateById(house);
        //增加操作记录
        operationRecord.setLaterData(JSON.toJSONString(house));
        operationRecord.setOperationType(OPERATION_TYPE_ENUM.HOUSE.getValue());
        operationRecordService.addOperationRecord(operationRecord);

        return Result.SUCCESS(house);
    }
    @LoginAuth(remark = "获取实有房屋分页列表")
    @ApiOperation("获取实有房屋分页列表")
    @GetMapping("houseListPage")
    public Result houseListPage(@ApiParam("页码")Integer page,@ApiParam("每页获取的数据量")Integer pageSize,
                                @ApiParam("审核状态") Integer auditState,@ApiParam("提交状态") Integer commitStatus,@ApiParam("如果需要当前用户的数据则传入") String token){
        Page pages=new Page(page.longValue(),pageSize.longValue());
        QueryWrapper queryWrapper=new QueryWrapper();
        if(StringUtils.isNotEmpty(token)){
            User user= TokenUtil.getTokenUser();
            queryWrapper.eq("create_user_id",user.getUserId());
        }
        if(null!=auditState){
            queryWrapper.eq("audit_state",auditState);
        }
        if(null!=commitStatus){
            queryWrapper.eq("commit_status",commitStatus);
        }
        return Result.SUCCESS(houseService.page(pages,queryWrapper));
    }
    @LoginAuth(remark = "审核实有房屋信息")
    @ApiOperation("审核实有房屋信息")
    @PutMapping("/auditHouse")
    public Result auditHouse(@RequestBody House house){
        User user=TokenUtil.getTokenUser();
        house.setAuditUser(StringUtils.isNotEmpty(user.getRealName())?user.getRealName():user.getNickName());
        house.setAuditUserId(user.getUserId());
        house.setAuditUserPhone(user.getPhone());
        houseService.updateById(house);
        return Result.SUCCESS(house);
    }
    @LoginAuth(remark = "获取房屋信息详情")
    @ApiOperation("获取房屋信息详情")
    @GetMapping("/getHouseById/{id}")
    public Result getHouseById(@PathVariable Integer id){

        return Result.SUCCESS(houseService.getById(id));
    }
}
