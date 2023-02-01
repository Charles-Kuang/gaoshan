package com.gaoshan.modules.wechat.person.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoshan.aspect.auth.LoginAuth;
import com.gaoshan.modules.wechat.address.domain.Address;
import com.gaoshan.modules.wechat.address.service.AddressService;
import com.gaoshan.modules.wechat.enumeration.OPERATION_TYPE_ENUM;
import com.gaoshan.modules.wechat.operation.record.domain.OperationRecord;
import com.gaoshan.modules.wechat.operation.record.service.OperationRecordService;
import com.gaoshan.modules.wechat.person.domain.Person;
import com.gaoshan.modules.wechat.person.service.PersonService;
import com.gaoshan.modules.wechat.user.domain.User;
import com.gaoshan.util.Result;
import com.gaoshan.util.TokenUtil;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Api(tags="实有人口相关接口")
@RestController
@RequestMapping("/person")
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private OperationRecordService operationRecordService;
    @Autowired
    private AddressService addressService;
    @LoginAuth(remark = "增加实有人口信息")
    @ApiOperation("增加实有人口信息")
    @PostMapping("/addPerson")
    public Result enteringPerson(@RequestBody Person person){
        /***************保存地址信息*********************/
        if(null==person.getAddressId()){
            Address address=new Address();
            address.setAddressDetail(person.getAddressDetail());
            address.setCreateTime(new Date());
            address.setRegionId(person.getRegionId());
            address.setLng(person.getLng());
            address.setLat(person.getLat());
            addressService.save(address);
            //设置保存后的地址id
            person.setAddressId(address.getAddressId());
        }
        /***************保存地址信息结束*********************/
        User user=TokenUtil.getTokenUser();
        person.setCreateTime(new Date());
        person.setCreateUser(StringUtils.isNotEmpty(user.getRealName())?user.getRealName():user.getNickName());
        person.setCreateUserId(user.getUserId());
        person.setCreateUserPhone(user.getPhone());
        personService.save(person);
        return Result.SUCCESS(person);
    }
    @LoginAuth(remark = "完善更新实有人口信息")
    @ApiOperation("完善更新实有人口信息")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Result.class)
    })
    @PutMapping("/updatePerson")
    public Result perfectPerson(@RequestBody Person person){
        OperationRecord operationRecord=new OperationRecord();
        Person prevDataPerson=personService.getById(person.getPersonId());
        operationRecord.setPrevData(JSON.toJSONString(prevDataPerson));
        personService.updateById(person);
        //增加操作记录
        operationRecord.setLaterData(JSON.toJSONString(person));
        operationRecord.setOperationType(OPERATION_TYPE_ENUM.PERSON.getValue());
        operationRecordService.addOperationRecord(operationRecord);
        return Result.SUCCESS(person);
    }
//    @LoginAuth(remark = "获取实有人口分页列表")
    @ApiOperation("获取实有人口分页列表")
    @GetMapping("personListPage")
    public Result personListPage(@ApiParam("页码")Integer page,@ApiParam("每页获取的数据量")Integer pageSize,
                                 @ApiParam("审核状态") Integer auditState,@ApiParam("提交状态") Integer commitStatus,@ApiParam("如果需要当前用户的数据则传入") String token){
        Page pages=new Page(page.longValue(),pageSize.longValue());
        QueryWrapper queryWrapper=new QueryWrapper();
        if(StringUtils.isNotEmpty(token)){
            User user=TokenUtil.getTokenUser();
            queryWrapper.eq("create_user_id",user.getUserId());
        }
        if(null!=auditState){
            queryWrapper.eq("audit_state",auditState);
        }
        if(null!=commitStatus){
            queryWrapper.eq("commit_status",commitStatus);
        }
        return Result.SUCCESS(personService.page(pages,queryWrapper));
    }
    @LoginAuth(remark = "审核实有人口信息")
    @ApiOperation("审核实有人口信息")
    @PutMapping("/auditPerson")
    public Result auditPerson(@RequestBody Person person){
        User user=TokenUtil.getTokenUser();
        person.setAuditUser(StringUtils.isNotEmpty(user.getRealName())?user.getRealName():user.getNickName());
        person.setAuditUserId(user.getUserId());
        person.setAuditUserPhone(user.getPhone());
        personService.updateById(person);
        return Result.SUCCESS(person);
    }
    @LoginAuth(remark = "获取人员信息详情")
    @ApiOperation("获取人员信息详情")
    @GetMapping("/getPersonById/{id}")
    public Result getPersonById(@PathVariable Integer id){
        return Result.SUCCESS(personService.getById(id));
    }
}
