package com.young.oceanisun;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@MapperScan("com.young.oceanisun.dao")
@EnableFeignClients
public class OceanisunApplication {

    public static void main(String[] args) {
        SpringApplication.run(OceanisunApplication.class, args);
    }
}
