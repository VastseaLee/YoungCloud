package com.young.dynamicseata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class DynamicSeataApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicSeataApplication.class, args);
    }

}
