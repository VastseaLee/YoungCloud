package com.young.oceanisun.controller;

import com.young.common.bean.WebResult;
import com.young.oceanisun.bean.Blog;
import com.young.oceanisun.service.BlogService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("blog")
public class BlogController {

    @Resource
    private BlogService blogService;

    @PostMapping("list")
    public WebResult list(@RequestBody Blog blog){
        return WebResult.success(blogService.list(blog));
    }

    @PostMapping("save")
    public WebResult save(@RequestBody Blog blog){
        blogService.save(blog);
        return WebResult.success("success");
    }
}
