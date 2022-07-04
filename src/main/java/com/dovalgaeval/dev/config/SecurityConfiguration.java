package com.dovalgaeval.dev.config;

import com.dovalgaeval.dev.component.JwtTokenProvider;
import com.dovalgaeval.dev.filter.JwtAuthenticationFilter;
import com.dovalgaeval.dev.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

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

    private final JwtTokenProvider jwtTokenProvider;

    /**
     *
     * filterChain springsecurity의 전반적인 설정
     *
     * @return SecurityFilterChain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        //authorizeRequests는 HttpServletRequest에 따라 접근을 제한한다. permitAll 누구나 접근가능하다.
        http.cors().and()
                .csrf().disable() //token을 사용하는 방식이기 때문에 csrf는 disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //session
                .and()
                .authorizeRequests()
//                .antMatchers("/**").permitAll()
                .and().authorizeRequests().antMatchers("/register","/","/h2-console/**","/signIn*").permitAll()
                .anyRequest().authenticated()
                .and().formLogin() //spring Security에서 제공하는 로그인화면을 사용하겠다.
                .loginPage("/")//spring Security에서 제공하는 로그인화면을 사용하지 않을 경우 사용
                .usernameParameter("userName")
                .permitAll()
                .and().logout().permitAll()
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint() //인증 예외발생했을때 어떻게 처리할지를 결정
                ;

        http.apply(new JwtSecurityConfig(jwtTokenProvider));
//        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers("/img/**","/css/**","/js/**","/vendeor/**");
        //resources/static에 있는 파일들을 무시한다는 설정
    }

}
