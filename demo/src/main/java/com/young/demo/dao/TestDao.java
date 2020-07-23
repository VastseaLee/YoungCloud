package com.young.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.young.demo.bean.User;

public interface TestDao extends BaseMapper<User> {
    
    User hello(Long id);
}
