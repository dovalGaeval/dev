package com.dovalgaeval.dev.service;

import com.dovalgaeval.dev.component.Encrypt;
import com.dovalgaeval.dev.domain.Member;
import com.dovalgaeval.dev.repository.MemberRepository;
import com.dovalgaeval.dev.request.MemberCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
*
* MemberService
*
* @author LJH
* 작성일 2022-06-28
**/
@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final Encrypt encrypt;

    private final PasswordEncoder passwordEncoder;

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

}
