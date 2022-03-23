package com.lzw.login_demo.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author LZW
 * @Description
 * @Date 2022-03-23 20:55
 * @Version 1.0
 */
public interface MyService {


    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
