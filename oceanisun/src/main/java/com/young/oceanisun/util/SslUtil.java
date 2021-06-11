package com.young.oceanisun.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

@Slf4j
public class SslUtil {
    private static volatile SSLContext sslContext = null;

    /**
     * ssl证书加载
     *
     * @param type     JKS、PKCS12 pfx文件，对应PKCS12
     * @param path     证书地址
     * @param password 证书密码
     * @return
     * @throws Exception
     */
    public static SSLContext createSSLContext(String type, String path, String password) throws Exception {
        log.info("[type]{} [path]{} [password]{}", type, path, password);
        if (null == sslContext) {
            synchronized (SslUtil.class) {
                if (null == sslContext) {
                    // 支持JKS、PKCS12（我项目中用的是阿里云免费申请的证书，下载tomcat解压后的pfx文件，对应PKCS12）
                    KeyStore ks = KeyStore.getInstance(type);
                    // 证书存放地址
                    InputStream ksInputStream = new FileInputStream(path);
                    ks.load(ksInputStream, password.toCharArray());
                    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                    kmf.init(ks, password.toCharArray());
                    sslContext = SSLContext.getInstance("TLS");
                    sslContext.init(kmf.getKeyManagers(), null, null);
                }
            }
        }
        return sslContext;
    }
}
