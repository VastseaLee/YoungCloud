package com.young.oceanisun;

import com.young.common.config.MyFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import java.util.HashSet;

@SpringBootApplication
@MapperScan("com.young.oceanisun.dao")
public class OceanisunApplication {

    public static void main(String[] args) {
        SpringApplication.run(OceanisunApplication.class, args);
    }

    @Bean
    public Filter getFilter(){
        return new MyFilter(new HashSet<>());
    }
}
