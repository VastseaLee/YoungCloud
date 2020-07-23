package com.young.common.bean;

import lombok.Data;

@Data
public class YoungUser {

    /**
     * 账号
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 身份证
     */
    private String idCard;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 手机号
     */
    private String phoneNum;
}
