package com.young.oceanisun.util;

import java.util.Base64;

public class Base64Util {

    public static String encode(byte[] bytes){
        Base64.Encoder encoder = Base64.getEncoder();
        return new String(encoder.encode(bytes));
    }
}
