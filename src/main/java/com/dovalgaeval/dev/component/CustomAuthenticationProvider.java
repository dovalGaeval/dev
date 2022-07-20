package com.dovalgaeval.dev.component;

import com.dovalgaeval.dev.domain.Member;
import com.dovalgaeval.dev.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
