package com.young.oceanisun.feign;

import com.young.common.bean.WebResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "auth-server")
public interface AuthFeign {

    @GetMapping("/main/selectUser")
    WebResult hello();
}
