package com.dovalgaeval.dev.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
*
* SecurityConfiguration의 springSecurity
*
* @author LJH
* 작성일 2022-06-30
**/
@Configuration
public class SecurityConfiguration {

    /**
     * passwordEncoder password 암호화
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     *
     * filterChain springsecurity의 전반적인 설정
     *
     * @return SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        //authorizeRequests는 HttpServletRequest에 따라 접근을 제한한다. permitAll 누구나 접근가능하다.
        http.csrf().disable() //token을 사용하는 방식이기 때문에 csrf는 disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //session
                .and().authorizeRequests().antMatchers("/","/register").permitAll() //로그인, 회원가입 페이지는 누구나 접근가능
                .and().formLogin() //spring Security에서 제공하는 로그인화면을 사용하겠다.
                .loginPage("/")//spring Security에서 제공하는 로그인화면을 사용하지 않을 경우 사용
                .usernameParameter("userName")
                .permitAll()
                .and().logout().permitAll();

        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers("/img/**","/css/**","/js/**","/vendeor/**");
        //resources/static에 있는 파일들을 무시한다는 설정
    }

}
