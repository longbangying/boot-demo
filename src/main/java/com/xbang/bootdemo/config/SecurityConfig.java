package com.xbang.bootdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig  extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Bean
    public BCryptPasswordEncoder  bCryptPasswordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }

    //先配置不用权限的 再配置需要权限的 不然不用权限的不生效
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*http.authorizeRequests()
                .anyRequest().authenticated()
                .mvcMatchers("/thymeleaf/**").hasRole("thymeleaf")
                .mvcMatchers("/redis/**").permitAll()
                .mvcMatchers("/level3/**").hasRole("Lev3");*/

        /*http.formLogin()
                .and()
                .authorizeRequests()
                .antMatchers("/redis/**").permitAll()
                .anyRequest().authenticated()
                .mvcMatchers("/tUser/**").hasAnyRole("admin")
                .mvcMatchers("/limit/**").hasAnyRole("member")
                .mvcMatchers("/tMoney/**").hasAnyRole("ordinary")
        ;*/

        http
                .cors().and()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/redis/**").permitAll()
                .antMatchers("/user/**").hasRole("ADMIN")
                .antMatchers("/limit/**").hasRole("MEMBER")
                .antMatchers("/tMoney/**").hasRole("ORDINARY")
                .and()
                .formLogin()
               /* .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login")*/;

    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsService;
    }
}
