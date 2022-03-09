package com.lzw.login_demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author LZW
 * @Description
 * @Date 2022-02-17 12:01
 * @Version 1.0
 */
@Controller
public class LoginController {

    @RequestMapping("/dologin")
    public String doLogin() {
        System.out.println("执行登录方法...");
        return "redirect:index.html";
    }

}
