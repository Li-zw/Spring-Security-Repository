package com.lzw.login_demo.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author LZW
 * @Description
 * @Date 2022-02-17 12:01
 * @Version 1.0
 */
@Controller
public class LoginController {


    /**
     * 登录验证 index.html
     *
     * @return
     */
    @RequestMapping("/dologin")
    public String login() {
        return "redirect:index.html";
    }


    /**
     * 跳转 index.html
     *
     * @return
     */
//    @Secured("ROLE_root")
    @PreAuthorize("hasRole('root')")
    @RequestMapping("/toIndex")
    public String toIndex() {
        return "redirect:index.html";
    }


    /**
     * 登录失败跳转 error.html
     *
     * @return
     */
    @RequestMapping("/toError")
    public String toError() {
        return "redirect:error.html";
    }


    /**
     * 页面跳转 demo.html
     *
     * @return
     */
    @GetMapping("/toDemo")
    @ResponseBody
    public String toDemo() {
        return "demo";
    }

}
