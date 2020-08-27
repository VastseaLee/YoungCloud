package com.young.oceanisun.controller;

import com.young.common.bean.WebResult;
import com.young.oceanisun.feign.AuthFeign;
import com.young.oceanisun.service.HomeService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@RestController
@RequestMapping("/home")
public class HomeController {

    @Resource
    private HomeService homeService;

    @GetMapping("/listImg")
    public WebResult listImg() {
        return WebResult.success(homeService.listImg());
    }

    @PostMapping("/replaceImg")
    public WebResult replaceImg(Integer id,MultipartFile file) {
        homeService.replaceImg(id,file);
        return WebResult.success("a");
    }
}
