package com.lzw.login_demo.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author LZW
 * @Description 自定义登录失败拦截器
 * @Date 2022-03-21 20:25
 * @Version 1.0
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private String url;

    public MyAuthenticationFailureHandler(String url){
        this.url = url;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.sendRedirect(url);
    }
}
