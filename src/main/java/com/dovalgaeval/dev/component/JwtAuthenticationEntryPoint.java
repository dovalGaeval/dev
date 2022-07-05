package com.dovalgaeval.dev.component;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
*
* JwtAuthenticationEntryPoint : 인증과정에서 실패하거나 인증을 위한 헤더정보를 보내지 않은 경우
 * 401에러 발생
*
* @author LJH
* 작성일 2022-07-05
**/
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.sendRedirect("/"); //로그인 페이지로 리다이렉트 되도록
    }
}
