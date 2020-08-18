package com.young.oceanisun.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.young.oceanisun.bean.Blog;

public interface BlogService {

    /**
     * 分页获取博客
     *
     * @param blog
     * @return
     */
    IPage<Blog> list(Blog blog);
}
