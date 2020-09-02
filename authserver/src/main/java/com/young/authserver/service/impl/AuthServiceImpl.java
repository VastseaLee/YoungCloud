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
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    @Resource
    private AuthDao authDao;

    private static final char[] codes = new char[]{
            'h', 'd', 'w', 'n', 'x', 'r',
            'i', 'g', 'l', 'm', 'u', 's',
            'y', 'q', 'k', 'b', 'f', 'a',
            'v', 'z', 'c', 'p', 't', 'j',
            'e', 'o'
    };

    @Override
    public String login(YoungUser user) {
        //先解密用户名密码
        Long num = LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8));
        int offset = num.intValue() % 1000 / 100;
        String account = decode(user.getAccount(), offset);
        //MD5处理用户输入的密码
        String password = getMD5(decode(user.getPassword(), offset));
        YoungUser result = new LambdaQueryChainWrapper<>(authDao)
                .eq(YoungUser::getAccount, account)
                .eq(YoungUser::getPassword, password)
                .one();
        if (result == null) {
            return "";
        }
        return JwtUtil.getJwt(result);
    }

    /**
     * 用户名密码解密
     *
     * @param str
     * @return
     */
    private String decode(String str, int num) {
        String newStr = "";
        char[] chars = str.toCharArray();
        for (char c : chars) {
            //获取该字符当前下标
            int i = 0;
            for (; i < codes.length; i++) {
                if (c == codes[i]) {
                    break;
                }
            }
            //往左移num个位置
            int index = i - num;
            if (index < 0) {
                index += 26;
            }
            newStr += (char)(index+97);
        }

        return newStr;
    }

    @Override
    public YoungUser selectUser(String token) {
        YoungUser user = new LambdaQueryChainWrapper<>(authDao)
                .eq(YoungUser::getAccount, JwtUtil.getAccount(token)).one();
        user.setPassword("");
        return user;
    }

    private static String getMD5(String content) {
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
