package com.dovalgaeval.dev.service;

import com.dovalgaeval.dev.domain.Member;
import com.dovalgaeval.dev.repository.MemberRepository;
import com.dovalgaeval.dev.request.MemberCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     *
     * save 회원저장
     *
     * @param memberCreate 
     * @return 
     */
    public void save(MemberCreate memberCreate){
        Member member = Member.builder()
                .userName(memberCreate.getUserName())
                .password(memberCreate.getPassword())
                .build();
        memberRepository.save(member);
    }

}
