package com.young.demo.feign;


import com.young.common.bean.WebResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "auth-server")
public interface DemoFeign {

    @GetMapping("/main/hah")
    WebResult hello();
}
