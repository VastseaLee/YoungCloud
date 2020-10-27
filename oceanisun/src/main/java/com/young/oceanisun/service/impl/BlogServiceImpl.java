package com.young.oceanisun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.young.oceanisun.bean.Blog;
import com.young.oceanisun.dao.BlogDao;
import com.young.oceanisun.service.BlogService;
import org.springframework.core.SpringVersion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Resource
    private BlogDao blogDao;

    /**
     * 分页获取博客
     *
     * @param blog
     * @return
     */
    @Override
    public IPage<Blog> list(Blog blog) {
        IPage<Blog> userPage = new Page<>(blog.getPageNum(), blog.getPageSize());
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Blog::getDelFlag,0).orderByDesc(Blog::getCreateTime);
        return blogDao.selectPage(userPage,queryWrapper);
    }

    /**
     * 保存博客
     *
     * @param blog
     */
    @Override
    public void save(Blog blog) {
        blog.setCreateTime(dtf.format(LocalDateTime.now()));
        blogDao.insert(blog);
    }

    /**
     * 修改博客
     *
     * @param blog
     */
    @Override
    public void update(Blog blog) {
        blogDao.updateById(blog);
    }

    /**
     * 根据id查询博客
     * @return
     */
    public Blog query(Integer id){
        return blogDao.selectById(id);
    }
}
