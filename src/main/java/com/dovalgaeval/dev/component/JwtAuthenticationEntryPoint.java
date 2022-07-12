package com.dovalgaeval.dev.component;

import com.dovalgaeval.dev.exception.ErrorCode;
import org.json.simple.JSONObject;
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
        String exception = String.valueOf(request.getAttribute("exception"));

        //토큰이 없을 때
        if(ErrorCode.UNKNOWN_TOKEN.getStatueCode().equals(exception)){
            setResponse(response, ErrorCode.UNKNOWN_TOKEN);
        }

        //만료된 토큰
        if(ErrorCode.EXPIRED_TOKEN.getStatueCode().equals(exception)){
            setResponse(response,ErrorCode.EXPIRED_TOKEN);
        }

        //잘못된 jwt 토큰
        if(ErrorCode.INVALID_TOKEN.getStatueCode().equals(exception)){
            setResponse(response,ErrorCode.INVALID_TOKEN);
        }

        //잘못된 JWT 서명
        if(ErrorCode.INVALID_SIGNATURE.getStatueCode().equals(exception)){
            setResponse(response,ErrorCode.INVALID_SIGNATURE);
        }

        //지원되지 않는 jwt 토큰
        if(ErrorCode.UNSUPPORTED_TOKEN.getStatueCode().equals(exception)){
            setResponse(response,ErrorCode.UNSUPPORTED_TOKEN);
        }

    }

    /**
     *
     * setResponse 발생한 에러를 response에 담아 출력하기 위한 메서드
     *
     * @param response,errorCode
     */
    private void setResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject json = new JSONObject();
        json.put("code",errorCode.getStatueCode());
        json.put("message",errorCode.getMessage());

        response.getWriter().print(json);
    }
}
