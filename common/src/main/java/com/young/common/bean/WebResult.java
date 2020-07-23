package com.young.common.bean;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 公共返回对象
 */
@Getter
@Setter
@NoArgsConstructor
public class WebResult<T> {
    /**
     * 数据
     */
    private T data;

    /**
     * 状态码（0成功 其余失败）
     */
    private int code;

    /**
     * 提示消息
     */
    private String msg;

    public static WebResult success(Object data,String msg){
        WebResult webResult = new WebResult();
        webResult.data = data;
        webResult.msg = msg;
        return webResult;
    }

    public static WebResult success(Object data){
        return success(data,"");
    }

    public static WebResult fail(int code,String msg){
        WebResult webResult = new WebResult();
        webResult.code = code;
        webResult.msg = msg;
        return webResult;
    }

    public static WebResult fail(int code){
        return fail(code,"");
    }
}
