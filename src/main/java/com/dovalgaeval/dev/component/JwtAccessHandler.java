package com.dovalgaeval.dev.component;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
*
* JwtAccessHandler 인가 예외발생했을때
* 인증된 객체이지만 권한이 없을때 동작
* @author LJH
* 작성일 2022-07-05
**/
@Component
public class JwtAccessHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN); //403
    }
}
