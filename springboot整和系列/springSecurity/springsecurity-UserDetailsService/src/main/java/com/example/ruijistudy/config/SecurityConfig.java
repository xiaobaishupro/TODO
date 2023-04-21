package com.example.ruijistudy.config;

/**
 * @author 努力的派大星
 * @version 1.0
 */

import com.example.ruijistudy.service.impl.SysUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;


@Configuration
@EnableWebSecurity //开启Security
@EnableGlobalMethodSecurity(prePostEnabled = true) //保证post之前的注解可以使用
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Resource
    private SysUserService sysUserService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //重写configure配置,将我们自己的校验密码器注入到该bean中
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(sysUserService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin() //下面是表单提交的配置
                .loginPage("/login") //指定登陆页面
                .loginProcessingUrl("/doLogin")//制定表单提交的请求地址
                .successForwardUrl("/home")//登录成功跳转页面
                .failureForwardUrl("/myError") //登录失败跳转页面
                .usernameParameter("username") //前端用户名的name属性，不设置默认是username
                .passwordParameter("password")//前端密码的name属性，不设置默认是password
                .and()
                .logout() //以下为注销设置
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .and()
                .authorizeRequests() //下面的都是授权的配置
                .antMatchers( "/login").permitAll()//访问此地址就不需要进行身份认证了
                .antMatchers("/admin").hasAnyAuthority("admin,manager")
                .antMatchers("/order").hasRole("order")
                .antMatchers("/hello").anonymous() //未登录的可以访问,登录的不可以
                .anyRequest() //任何请求
                .authenticated() //访问任何资源都需要身份认证
                .and()
                .csrf().disable(); // 禁用 Spring Security 自带的跨域处理

    }


}
