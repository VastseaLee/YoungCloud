package com.young.oceanisun.controller;

import com.young.common.bean.WebResult;
import com.young.oceanisun.bean.Blog;
import com.young.oceanisun.cfg.valid.Update;
import com.young.oceanisun.service.BlogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.groups.Default;

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
    public WebResult save(@Validated @RequestBody Blog blog){
        blogService.save(blog);
        return WebResult.success();
    }

    @PostMapping("update")
    public WebResult update(@Validated({Update.class,Default.class}) @RequestBody Blog blog){
        blogService.update(blog);
        return WebResult.success();
    }
}
