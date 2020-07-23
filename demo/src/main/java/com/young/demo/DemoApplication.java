package com.young.demo;

import com.young.common.config.MyFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@MapperScan("com.young.demo.dao")
@EnableFeignClients
public class DemoApplication {

    @Value("${white.list}")
    private Set<String> whiteSet;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public Filter getFilter(){
        return new MyFilter(whiteSet);
    }
}
