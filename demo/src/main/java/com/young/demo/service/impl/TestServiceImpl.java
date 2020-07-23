package com.young.demo.service.impl;

import com.young.common.bean.YoungUser;
import com.young.common.util.JwtUtil;
import com.young.demo.bean.User;
import com.young.demo.dao.TestDao;
import com.young.demo.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("testService")
public class TestServiceImpl implements TestService {

    @Resource
    private TestDao testDao;

    @Override
    public User hello(Long id) {
        return testDao.selectById(id);
    }
}
