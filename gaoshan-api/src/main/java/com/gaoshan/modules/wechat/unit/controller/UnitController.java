package com.gaoshan.modules.wechat.unit.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gaoshan.aspect.auth.LoginAuth;
import com.gaoshan.modules.wechat.enumeration.OPERATION_TYPE_ENUM;
import com.gaoshan.modules.wechat.operation.record.domain.OperationRecord;
import com.gaoshan.modules.wechat.operation.record.service.OperationRecordService;
import com.gaoshan.modules.wechat.person.domain.Person;
import com.gaoshan.modules.wechat.unit.domain.Unit;
import com.gaoshan.modules.wechat.unit.service.UnitService;
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
 * @author lumberer
 * @date 2022-11-25
 * 实有单位信息相关接口
 */
@Api(tags="实有单位信息相关接口")
@RestController
@RequestMapping("/unit")
public class UnitController {
    @Autowired
    private UnitService unitService;
    @Autowired
    private OperationRecordService operationRecordService;
    @LoginAuth(remark = "增加实有单位信息")
    @ApiOperation("增加实有单位信息")
    @PostMapping("/addUnit")
    public Result enteringUnit(@RequestBody Unit unit){
        User user=TokenUtil.getTokenUser();
        unit.setCreateTime(new Date());
        unit.setCreateUser(StringUtils.isNotEmpty(user.getRealName())?user.getRealName():user.getNickName());
        unit.setCreateUserId(user.getUserId());
        unit.setCreateUserPhone(user.getPhone());
        unitService.save(unit);
        return Result.SUCCESS(unit);
    }
    @LoginAuth(remark = "完善更新实有单位信息")
    @ApiOperation("完善更新实有单位信息")
    @PutMapping("/updateUnit")
    public Result perfectUnit(@RequestBody Unit unit){
        OperationRecord operationRecord=new OperationRecord();
        Unit prevDataUnit=unitService.getById(unit.getUnitId());
        operationRecord.setPrevData(JSON.toJSONString(prevDataUnit));
        unitService.updateById(unit);
        //增加操作记录
        operationRecord.setLaterData(JSON.toJSONString(unit));
        operationRecord.setOperationType(OPERATION_TYPE_ENUM.PERSON.getValue());
        operationRecordService.addOperationRecord(operationRecord);
        return Result.SUCCESS(unit);
    }
    @LoginAuth(flag = false,remark = "获取实有单位分页信息")
    @ApiOperation("获取实有单位分页信息")
    @GetMapping("unitListPage")
    public Result unitListPage(@ApiParam("页码")Integer page,@ApiParam("每页获取的数据量")Integer pageSize,
                               @ApiParam("审核状态")Integer auditState,@ApiParam("提交状态")Integer commitStatus,@ApiParam("如果需要当前用户的数据则传入") String token){
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
        return Result.SUCCESS(unitService.page(pages,queryWrapper));
    }
    @LoginAuth(remark = "审核实有人口信息")
    @ApiOperation("审核实有人口信息")
    @PutMapping("/auditUnit")
    public Result auditUnit(@RequestBody Unit unit){
        User user=TokenUtil.getTokenUser();
        unit.setAuditUser(StringUtils.isNotEmpty(user.getRealName())?user.getRealName():user.getNickName());
        unit.setAuditUserId(user.getUserId());
        unit.setAuditUserPhone(user.getPhone());
        unitService.updateById(unit);
        return Result.SUCCESS(unit);
    }

    @LoginAuth(remark = "获取房屋信息详情")
    @ApiOperation("获取房屋信息详情")
    @GetMapping("/getUnitById/{id}")
    public Result getUnitById(@PathVariable Integer id){

        return Result.SUCCESS(unitService.getById(id));
    }
}
