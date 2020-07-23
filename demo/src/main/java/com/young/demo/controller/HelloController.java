package com.young.demo.controller;

import com.young.common.bean.WebResult;
import com.young.demo.feign.DemoFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {
    @Resource
    private DemoFeign demoFeign;

    @GetMapping("/search")
    public String search(){
        log.info("I'm info");
        log.error("I'm erro");
        return "这是个白名单";
    }

    @GetMapping("/query")
    public String query(){
        return "这不是个白名单";
    }

    @GetMapping("/hh")
    public WebResult hh(){
        return demoFeign.hello();
    }
}
