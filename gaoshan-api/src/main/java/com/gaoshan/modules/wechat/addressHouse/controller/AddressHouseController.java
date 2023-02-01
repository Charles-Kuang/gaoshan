package com.gaoshan.modules.wechat.addressHouse.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gaoshan.modules.common.CommonController;
import com.gaoshan.modules.wechat.addressHouse.domain.AddressHouse;
import com.gaoshan.modules.wechat.addressHouse.service.AddressHouseService;
import com.gaoshan.modules.wechat.enumeration.COMMIT_STATUS;
import com.gaoshan.modules.wechat.person.domain.Person;
import com.gaoshan.modules.wechat.person.service.PersonService;
import com.gaoshan.modules.wechat.user.domain.User;
import com.gaoshan.util.Result;
import com.gaoshan.util.TokenUtil;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.math3.analysis.function.Add;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

@Api(tags = "新录入相关接口")
@Controller
@RequestMapping("/addressHouse")
public class AddressHouseController extends CommonController<AddressHouse> {
    @Autowired
    private AddressHouseService addressHouseService;
    @Autowired
    private PersonService personService;
    @Override
    protected IService<AddressHouse> getCoreService() {
        return this.addressHouseService;
    }
    @Override
    @ApiOperation("分页数据,查询参数where")
    @GetMapping("/listPage")
    @ResponseBody
    public Result listPage(Integer page,Integer pageSize,String where){
        Page<AddressHouse> pageParams=new Page<AddressHouse>(page,pageSize);
        QueryWrapper<AddressHouse> queryWrapper=new QueryWrapper<AddressHouse>();
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
        User user= TokenUtil.getTokenUser();
        if(null!=user){
            queryWrapper.eq("create_user_id",user.getUserId());
        }
        IPage<AddressHouse> pageData=getCoreService().page(pageParams,queryWrapper);
        return Result.SUCCESS(pageData);
    }
    @Override
    @GetMapping("/getDataById/{id}")
    @ResponseBody
    public Result getDataById(@PathVariable Serializable id, String where){
        AddressHouse t;
        QueryWrapper<AddressHouse> queryWrapper=new QueryWrapper<AddressHouse>();
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
        if(null!=t){
            QueryWrapper<Person> personQueryWrapper=new QueryWrapper<>();
            personQueryWrapper.eq("address_id",t.getId());
            personQueryWrapper.eq("commit_status", COMMIT_STATUS.PASS.getStatus());
            Page page=new Page<>(1,5);
            t.setPersonList(personService.page(page,personQueryWrapper).getRecords());
        }
        return Result.SUCCESS(t);
    }
}
