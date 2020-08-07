package com.young.authserver.controller;

import com.young.authserver.service.AuthService;
import com.young.common.bean.WebResult;
import com.young.common.bean.YoungUser;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("main")
public class AuthController {

    @Resource
    private AuthService authService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @PostMapping("login")
    public WebResult login(@RequestBody YoungUser user) {
        String token = authService.login(user);
        return StringUtils.isEmpty(token) ? WebResult.fail(01) : WebResult.success(token);
    }

    /**
     * 查询当前用户
     *
     * @param request
     * @return
     */
    @GetMapping("selectUser")
    public WebResult selectUser(HttpServletRequest request) {
        return WebResult.success(authService.selectUser(request.getHeader("access_token")));
    }

    @GetMapping("love")
    public WebResult hah() {
        return WebResult.success("Love Sun");
    }
}
