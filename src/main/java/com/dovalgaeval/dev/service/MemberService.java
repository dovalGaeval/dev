package com.dovalgaeval.dev.service;

import com.dovalgaeval.dev.config.Encrypt;
import com.dovalgaeval.dev.config.Salt;
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

    private final Salt salt;

    /**
     *
     * save 회원저장
     *
     * @param memberCreate
     */
    public void save(MemberCreate memberCreate){
        String rawSalt = salt.getSalt();
        Member member = Member.builder()
                .userName(encrypt.getEncrypt(memberCreate.getUserName()))
                .password(encrypt.getEncrypt(memberCreate.getPassword()+rawSalt))
                .salt(encrypt.getEncrypt(rawSalt))
                .build();
        memberRepository.save(member);
    }

}
