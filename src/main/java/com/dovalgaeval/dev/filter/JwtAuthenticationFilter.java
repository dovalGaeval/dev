package com.dovalgaeval.dev.filter;

import com.dovalgaeval.dev.component.JwtTokenProvider;
import com.dovalgaeval.dev.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//
/**
*
* JwtAuthenticationFilter : HTTP Request에서 토큰을 읽어 들여 정상 토큰이면 Security Context에 저장
* OncePerRequestFilter 모든 서블릿에 일관된 요청을 처리하기 위해 만들어진 필터
* @author LJH
* 작성일 2022-07-03
**/
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     *
     * doFilterInternal jwt인증
     *
     * @param request,response,filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String jwt = jwtTokenProvider.getJwtFromRequest(request); //header로부터 bearer 토큰을 가져옴
            if(StringUtils.isNotEmpty(jwt) && jwtTokenProvider.validateToken(jwt,request)){ //token 체크
                String userName = jwtTokenProvider.getUserIdFromJWT(jwt);

                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userName, null, null);

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                //정상 토큰이면 토큰을 통해 생성한 Authentication 객체를 SecurityContext에 저장
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }catch (Exception e){
            SecurityContextHolder.clearContext();
            log.error("Could not set user authentication in security context",e);
        }
        
        filterChain.doFilter(request,response); //다음 필터 체인 실행

    }



}
