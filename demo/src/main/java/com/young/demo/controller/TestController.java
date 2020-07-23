package com.young.demo.controller;

import com.young.demo.bean.User;
import com.young.demo.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/test")
@RefreshScope(proxyMode = ScopedProxyMode.DEFAULT)
@Api(tags = "测试接口")
public class TestController {

    @Resource
    private TestService testService;

    @Value("${young.value}")
    private String value;

    @ApiOperation(value="根据id获取用户", notes="根据id获取用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "主键")
    })
    @GetMapping("/hello")
    public User hello(Long id){
        return testService.hello(id);
    }

    @GetMapping("/con")
    public String con(){
        return value;
    }

    @GetMapping("/search")
    public String search(){
        return "I'm Test";
    }
}
