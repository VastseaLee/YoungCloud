package com.young.common.util;

import com.young.common.bean.YoungUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import io.jsonwebtoken.jackson.io.JacksonSerializer;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static Integer seconds = 1800;

    private static Key key = new SecretKeySpec(
            Base64.encodeBase64("hiuaHShsoH465A4DsJ S -0i0a-dA4D4da4DQR E".getBytes()),
            SignatureAlgorithm.HS256.getJcaName());

    private static String jwtKey = "1217";

    //根据用户获取jwt
    public static String getJwt(YoungUser user) {
        Date expiration = new Date(System.currentTimeMillis() + seconds * 1000);
        String jwt = Jwts
                .builder()
                //加密算法对应的密钥，这里使用的是HS256加密算法
                .signWith(key)
                //expTime是过期时间
                .setExpiration(expiration)
                .claim("account", user.getAccount())
                .claim("code", jwtKey)
                .serializeToJsonWith(new JacksonSerializer<>())
                .compact();
        return jwt;
    }

    //验证token有效性
    public static boolean isJwtValid(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .deserializeJsonWith(new JacksonDeserializer<>())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            if (jwtKey.equals(claims.get("code"))) {
                return true;
            }
        } catch (Exception e) {}
        return false;
    }

    public static String getAccount(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .deserializeJsonWith(new JacksonDeserializer<>())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
           return (String) claims.get("account");
        } catch (Exception e) {}
        return "";
    }
}
