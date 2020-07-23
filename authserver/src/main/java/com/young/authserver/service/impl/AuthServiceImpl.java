package com.young.authserver.service.impl;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.young.authserver.dao.AuthDao;
import com.young.authserver.service.AuthService;
import com.young.common.bean.YoungUser;
import com.young.common.util.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthDao authDao;

    @Override
    public String login(YoungUser user) {
        //MD5处理用户输入的密码
        String password = getMD5(user.getPassword());
        YoungUser result = new LambdaQueryChainWrapper<>(authDao)
                .eq(YoungUser::getAccount, user.getAccount())
                .eq(YoungUser::getPassword, password)
                .one();
        return JwtUtil.getJwt(result);
    }

    @Override
    public YoungUser selectUser(String token) {
        YoungUser user = new LambdaQueryChainWrapper<>(authDao)
                .eq(YoungUser::getAccount, JwtUtil.getAccount(token)).one();
        user.setPassword("");
        return user;
    }

    private String getMD5(String content) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BigInteger digest = new BigInteger(md5.digest(content.getBytes()));
            return digest.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
