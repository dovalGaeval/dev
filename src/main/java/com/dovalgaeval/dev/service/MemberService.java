package com.dovalgaeval.dev.service;

import com.dovalgaeval.dev.Role;
import com.dovalgaeval.dev.component.Encrypt;
import com.dovalgaeval.dev.component.JwtTokenProvider;
import com.dovalgaeval.dev.domain.Member;
import com.dovalgaeval.dev.repository.MemberRepository;
import com.dovalgaeval.dev.request.MemberCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
*
* MemberService
*
* @author LJH
* 작성일 2022-06-28
**/
@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final Encrypt encrypt;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    
    /**
     *
     * save 회원저장
     *
     * @param memberCreate
     */
    public void save(MemberCreate memberCreate){
        Member member = Member.builder()
                .userName(encrypt.encryptAES256(memberCreate.getUserName())) //userName 암호화하기
                .password(passwordEncoder.encode(memberCreate.getPassword())) //비밀번호 암호화하기
                .build();
        memberRepository.save(member);
    }

    /**
     *
     * loadUserByUsername 회원조회
     *
     * @param username 조회하고자하는 회원의 이메일주소
     * @return Member  조회된 회원
     */
    @Override
    public Member loadUserByUsername(String username){
        Member member = memberRepository.findByUserName(encrypt.encryptAES256(username))
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 회원입니다."));

        return Member.builder().userName(encrypt.decryptAES256(member.getUsername()))
                .password(member.getPassword())
                .role(Role.ROLE_USER).build();
    }
    
    public String login(MemberCreate request){
        log.info("memberService login 시작");
        Member member = loadUserByUsername(request.getUserName());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getUserName(), null, null);
        log.info("token : " ,token);

        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(token);
        String jwt = jwtTokenProvider.createToken(authenticate);
        log.info("jwt", jwt);

        return jwt;
    }

}
