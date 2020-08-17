package com.young.common.config;


import com.young.common.util.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class MyFilter implements Filter {
    private static Set<String> whiteSet;

    private static String loginUrl = "http://www.oceanisun.top";

    public MyFilter(){
        whiteSet = new HashSet<>();
    }

    public MyFilter(Set<String> set){
        whiteSet = set;
    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        //首先验证是否是白名单接口
        String uri = request.getRequestURI();
        if(whiteSet.contains(uri)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        //验证jwt有效性
        String token = request.getHeader("access_token");
        if(JwtUtil.isJwtValid(token)){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        response.sendRedirect(loginUrl);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }
}
