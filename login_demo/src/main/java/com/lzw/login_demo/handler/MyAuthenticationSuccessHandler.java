package com.lzw.login_demo.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author LZW
 * @Description AuthenticationSuccessHandler 实现类
 * @Date 2022-03-10 10:50
 * @Version 1.0
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private String url;

    public MyAuthenticationSuccessHandler(String url) {
        this.url = url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        User user = (User) authentication.getPrincipal();
        System.out.println("username = " + user.getUsername());
        System.out.println("password = " + user.getPassword()); // 安全问题会输出null
        System.out.println("authorities = " + user.getAuthorities());

        httpServletResponse.sendRedirect(url);
    }
}
