package com.dovalgaeval.dev.filter;

import com.dovalgaeval.dev.domain.Member;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * CustomUsernamePasswordFilter
 * UsernamePasswordAuthenticationFilter를 상속받음
 * 해당 필터의 특징은
 * 1. 필터를 거치고 다음 필터로 동작을 넘기지 않는다. 인증 성공 여부에 따른 메서드
 *    successfulAuthentication/unSuccessfulAuthentication를 구현해야 하는 이유다.
 * 2. 해당 필터는 /login에 접근할 때만 동작한다. 그렇기 때문에 내가 원하는 url에서 필터가 동작하길
 *    원한다면 setFilterProcessUrl()로 Url를 설정해줘야 작동한다.
 *
 * @author LJH
 * 작성일 2022-07-20
**/
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUsernamePasswordFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        log.info("usernamePassword_filter");

        ObjectMapper objectMapper = new ObjectMapper();

        Member member = null;

        try {
            member = objectMapper.readValue(request.getInputStream(), Member.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //usernamePassword token 생성
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(member.getUsername(), member.getPassword());

        //authenticate() : 함수가 호출되면 인증 provider가 userDetailservice의
        //loadUserByUsername를 호출하고 UserDetails를 리턴받아서 토큰의 두번째 파라미터와 db값의 getPassword를 비교해서 동일하면 authentication 객체를 만들어서 필터체인으로 리턴해준다.
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        log.info("Authentication : " + authenticate.getName());

        return authenticate;
    }



}
