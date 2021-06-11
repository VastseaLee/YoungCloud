package com.young.oceanisun.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SSLCertificate {

    public static String PATH;


    public static String PASSWORD;


    @Value("${ssl.certificate.path}")
    public void setPath(String path) {
        SSLCertificate.PATH = path;
    }

    @Value("${ssl.certificate.password}")
    public void setPassword(String password) {
        SSLCertificate.PASSWORD = password;
    }
}
