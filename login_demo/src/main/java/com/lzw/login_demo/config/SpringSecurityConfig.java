package com.lzw.login_demo.config;

import com.lzw.login_demo.handler.MyAccessHandler;
import com.lzw.login_demo.handler.MyAuthenticationFailureHandler;
import com.lzw.login_demo.handler.MyAuthenticationSuccessHandler;
import com.lzw.login_demo.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @Author LZW
 * @Description Spring Security 配置类
 * @Date 2022-03-09 15:41
 * @Version 1.0
 */
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private MyAccessHandler myAccessHandler;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PersistentTokenRepository persistentTokenRepository;


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
//                .loginPage("/login.html")
                .loginPage("/showLogin")
                // 登录成功跳转的页面,Post 请求,否则回报 405 错误
                .successForwardUrl("/toIndex")
                // 登录成功后的处理器,不能和 successForwardUrl 共存,否则会报错
//                .successHandler(new MyAuthenticationSuccessHandler("/index.html"))

                // 登录失败跳转的页面,Post 请求,否则回报 405 错误
                .failureForwardUrl("/toError");

                // 登录失败后的处理器
//                .failureHandler(new MyAuthenticationFailureHandler("/error.html"))

//                // 自定义登录参数名,须和表单字段一致
//                .usernameParameter("user")
//                .passwordParameter("pwd");


        // 授权认证
        http.authorizeRequests()
                // 放行 error.html login.html
                .antMatchers("/error.html").permitAll()
//                .antMatchers("/login.html").permitAll()
//                .antMatchers("/login.html").access("permitAll()")
                .antMatchers("/showLogin").access("permitAll()")
                /**
                 * 放行静态资源
                 * 推荐 .antMatchers("/js/**", "/css/**", "/images/**").permitAll()
                 */
                .antMatchers("/js/**", "/css/**", "/images/**").permitAll()
//                .antMatchers("/**/*.png").permitAll()
                // 正则表达式放行png文件
//                .regexMatchers(".+[.]png").permitAll()

                // 限定请求方法
//                .regexMatchers(HttpMethod.POST, "/toDemo").permitAll()

                /**
                 * 配置 mvcMtchers ,两者等价
                 * .mvcMtchers("/toDemo").servletPath("/api").permitAll()
                 * .antMatchers("/api/toDemo").permitAll()
                 *
                 */
//                .mvcMtchers("/toDemo").servletPath("/api").permitAll()
//                .antMatchers("/api/toDemo").permitAll()

                /**
                 * 控制权限 区分大小写
                 */
//                .antMatchers("/main.html").hasAuthority("admin")
//                .antMatchers("/main.html").hasAnyAuthority("admin","admiN")
//                .antMatchers("/main.html").hasRole("root")
//                .antMatchers("/main.html").access("hasRole('root')")

                // 限制IP地址
//                .antMatchers("/main.html").hasIpAddress("127.0.0.1")

                // 所有请求都必须认证,必须在登录后后被访问
                .anyRequest()
                .authenticated();

        // 自定义权限处理器
//                .anyRequest().access("@myServiceImpl.hasPermission(request,authentication)");


        // 关闭 csrf 防护
//        http.csrf().disable();

        /**异常处理*/
        http.exceptionHandling()
                .accessDeniedHandler(myAccessHandler);


        // 记住我
        http.rememberMe()
                // token 失效时间60s
                .tokenValiditySeconds(60)
                // 自定义登录逻辑
                .userDetailsService(userDetailsService)
                // 持久层对象
                .tokenRepository(persistentTokenRepository);


        // 退出登录
        http.logout().logoutSuccessUrl("/login.html");

    }


    @Bean
    public PasswordEncoder getPwd() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        // 自动建表,第一次启动的需要，第二次启动注释掉
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return jdbcTokenRepository;
    }

}
