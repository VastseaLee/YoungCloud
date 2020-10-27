package com.young.oceanisun.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.young.oceanisun.bean.Blog;

public interface BlogService {

    /**
     * 分页获取博客
     *
     * @param blog
     * @return
     */
    IPage<Blog> list(Blog blog);

    /**
     * 保存博客
     *
     * @param blog
     */
    void save(Blog blog);

    /**
     * 修改博客
     *
     * @param blog
     */
    void update(Blog blog);

    /**
     * 根据id查询详情
     *
     * @return
     */
    Blog query(Integer id);
}
