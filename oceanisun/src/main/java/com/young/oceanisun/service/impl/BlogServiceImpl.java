package com.young.oceanisun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.young.oceanisun.bean.Blog;
import com.young.oceanisun.dao.BlogDao;
import com.young.oceanisun.service.BlogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogDao blogDao;

    @Override
    public IPage<Blog> list(Blog blog) {
        IPage<Blog> userPage = new Page<>(blog.getPageNum(), blog.getPageSize());
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Blog::getDelFlag,0);
        return blogDao.selectPage(userPage,queryWrapper);
    }
}
