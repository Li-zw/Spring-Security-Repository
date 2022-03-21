package com.lzw.login_demo.config;

import com.lzw.login_demo.handler.MyAuthenticationFailureHandler;
import com.lzw.login_demo.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author LZW
 * @Description Spring Security 配置类
 * @Date 2022-03-09 15:41
 * @Version 1.0
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     * 自定义登录页面
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
                // 请求地址与表单 action 地址一致
                .loginProcessingUrl("/login")
                // 配置自定义登录页
                .loginPage("/login.html")
                // 登录成功跳转的页面,Post 请求,否则回报 405 错误
//                .successForwardUrl("/toIndex")
                // 登录成功后的处理器,不能和 successForwardUrl 共存,否则会报错
                .successHandler(new MyAuthenticationSuccessHandler("http://www.baidu.com"))

                // 登录失败跳转的页面,Post 请求,否则回报 405 错误
//                .failureForwardUrl("/toError")

                // 登录失败后的处理器
                .failureHandler(new MyAuthenticationFailureHandler("/error.html"))

                // 自定义登录参数名,须和表单字段一致
                .usernameParameter("user")
                .passwordParameter("pwd");


        // 授权认证
        http.authorizeRequests()
                // 放行 error.html login.html
                .antMatchers("/error.html").permitAll()
                .antMatchers("/login.html").permitAll()
                // 所有请求都必须认证,必须在登录后后被访问
                .anyRequest()
                .authenticated();


        // 关闭 csrf 防护
        http.csrf().disable();
    }


    @Bean
    public PasswordEncoder getPwd() {
        return new BCryptPasswordEncoder();
    }


}
