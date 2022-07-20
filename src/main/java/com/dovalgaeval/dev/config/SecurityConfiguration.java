package com.dovalgaeval.dev.config;

import com.dovalgaeval.dev.component.*;
import com.dovalgaeval.dev.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
*
* SecurityConfiguration의 springSecurity
*
* @author LJH
* 작성일 2022-06-30
**/
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessHandler jwtAccessHandler;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

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
        http
            .csrf().disable() //token을 사용하는 방식이기 때문에 csrf는 disable
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //session을 사용하지 않음
            .and().addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) //UsernamePasswordAuthenticationFilter 전에 jwt인증을 먼저 하겠다는 의미
            .authorizeRequests()
            .and().authorizeRequests().antMatchers("/","/h2-console/**","/login","/register").permitAll()
            .anyRequest().authenticated()
            .and().formLogin() //spring Security에서 제공하는 로그인화면을 사용하겠다.
            .loginPage("/")//spring Security에서 제공하는 로그인화면을 사용하지 않을 경우 사용
            .usernameParameter("userName") //spring Security에서는 파라미터로 username을 사용하는데 변경하고 싶을 때 사용
            .permitAll()
            .and().logout().permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint) //인증 예외발생했을때 어떻게 처리할지를 결정
            .accessDeniedHandler(jwtAccessHandler) //인가 예외발생했을때
            ;

        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers("/img/**","/css/**","/js/**","/vendor/**");
        //resources/static에 있는 파일들을 무시한다는 설정
    }

}
