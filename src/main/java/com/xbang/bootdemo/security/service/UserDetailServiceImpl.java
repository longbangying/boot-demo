package com.xbang.bootdemo.security.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Slf4j
@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private final String [] roles_arr = {"admin","member","ordinary"};

    private final String [] users_arr = {"admin","xbang","user"};

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        log.info("user {} requested login.....",s);
        //List<String> roles = Arrays.asList(roles_arr);
        List<String> users = Arrays.asList(users_arr);

        if(users.isEmpty() || !users.contains(s)){
            throw new UsernameNotFoundException("user " + s  +" not found.");
        }
        List<SimpleGrantedAuthority> roless = new ArrayList<>(3);
        String password = "";
        // 角色名必须要加前缀 ROLE_
        if("admin".equals(s)){
            roless.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            roless.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
            roless.add(new SimpleGrantedAuthority("ROLE_ORDINARY"));
            password = bCryptPasswordEncoder.encode("xbang");
        }else if("xbang".equals(s)){
            roless.add(new SimpleGrantedAuthority("ROLE_MEMBER"));
            roless.add(new SimpleGrantedAuthority("ROLE_ORDINARY"));
            password = bCryptPasswordEncoder.encode("xbang");
        }else if("user".equals(s)){
            roless.add(new SimpleGrantedAuthority("ROLE_ORDINARY"));
            password = bCryptPasswordEncoder.encode("user");
        }else{
           //no roles
            password = bCryptPasswordEncoder.encode("xbang");
        }
        log.info("password:{}",password);
        log.info("user {} role is {}",s,roless.toArray());
        User userDetails = new User(s,password,roless);
        return userDetails;
    }



}
