package com.dovalgaeval.dev.component;

import com.dovalgaeval.dev.domain.Member;
import com.dovalgaeval.dev.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * CustomAuthenticationProvider
 * AuthenticationProvider 구현받은 class로 UserDetails에서 가져온 user와 UsernamePasswordAuthentication Token을 비교하여 인증완료된
 * 객체를 반환하는 역할을 한다.
 *
 * @author LJH
 * 작성일 2022-07-23
**/
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberService memberService;
    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName(); //userName
        String password = String.valueOf(authentication.getCredentials()); //password

        Member member = memberService.loadUserByUsername(userName);

        //비밀번호 확인하기
        if(!encoder.matches(password, member.getPassword())){
            throw new BadCredentialsException("password is not matched");
        }

        return new UsernamePasswordAuthenticationToken(userName,password, member.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
