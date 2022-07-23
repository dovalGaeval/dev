package com.dovalgaeval.dev.component;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * CustomAuthenticationSuccessHandler
 * login에 성공한 후 jwt token을 만들기 위한 class
 *
 * @author LJH
 * 작성일 2022-07-23
**/
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private JwtTokenProvider jwtTokenProvider;

    public CustomAuthenticationSuccessHandler(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setHeader("Authorization","Bearer " + jwtTokenProvider.createToken(authentication));
    }

}
