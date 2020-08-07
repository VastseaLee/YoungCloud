package com.young.configserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("hh")
public class TestController {

    @GetMapping("xx")
    public String test(){
        return "OK";
    }
}
