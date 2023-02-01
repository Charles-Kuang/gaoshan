package com.gaoshan.modules.test.controller;

import com.gaoshan.aspect.auth.LoginAuth;
import com.gaoshan.modules.test.TestService;
import com.gaoshan.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TestController {
    @Autowired(required = false)
    private TestService testService;
    @LoginAuth
    @RequestMapping("/test")
    public Result test() throws Exception {
        return Result.SUCCESS(testService.getById(1));
    }
}
