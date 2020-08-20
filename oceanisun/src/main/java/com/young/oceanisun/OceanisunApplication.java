package com.young.oceanisun;

import com.alibaba.druid.support.http.StatViewServlet;
import com.young.common.config.MyFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.HashSet;

@SpringBootApplication
@MapperScan("com.young.oceanisun.dao")
public class OceanisunApplication {

    public static void main(String[] args) {
        SpringApplication.run(OceanisunApplication.class, args);
    }

    @Bean
    public Filter getFilter(){
        return new MyFilter();
    }

//    @Bean
//    public ServletRegistrationBean statViewServlet(){
//        ServletRegistrationBean<StatViewServlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
//
//        HashMap<String, String> initParameters = new HashMap<>();
//        // 增加配置项
//        initParameters.put("loginUsername","admin");
//        initParameters.put("loginPassword","123456");
//        initParameters.put("allow","localhost");     //如果为空所有人都可以访问，如果localhost本机可以访问，如果具体的ip值则具体的值
//        // 关于其他配置可以在类ResourceServlet下查看
//
//        // 禁止xu访问       initParameters.put("xu","192.168.1.**");
//
//        // 后台需要有人登陆
//        bean.setInitParameters(initParameters);
//        return bean;
//    }
}
