package com.lzw.login_demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author LZW
 * @Description
 * @Date 2022-03-09 15:45
 * @Version 1.0
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("执行了 loadUserByUsername() 方法... ");
        // 查询数据库用户名是否存在,如果不存在就抛出 UsernameNotFoundException 异常
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("用户名不存在...");
        }
        // 把查询出来的密码（注册时已经加密过）进行解析，或者直接把密码放入构造方法
        String password = passwordEncoder.encode("123");
        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,normal,ROLE_root,/index.html"));

    }
}
