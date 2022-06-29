package com.dovalgaeval.dev.service;

import com.dovalgaeval.dev.config.Encrypt;
import com.dovalgaeval.dev.domain.Member;
import com.dovalgaeval.dev.repository.MemberRepository;
import com.dovalgaeval.dev.request.MemberCreate;
import lombok.RequiredArgsConstructor;
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

    /**
     *
     * save 회원저장
     *
     * @param memberCreate
     */
    public void save(MemberCreate memberCreate){
        Member member = Member.builder()
                .userName(encrypt.encryptAES256(memberCreate.getUserName())) //userName 암호화하기
                .password(memberCreate.getPassword())
                .build();
        memberRepository.save(member);
    }

}
