package com.young.oceanisun.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.young.oceanisun.bean.Blog;

import java.util.List;

public interface BlogService {

    IPage<Blog> list(Blog blog);
}
