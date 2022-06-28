package com.dovalgaeval.dev.service;

import com.dovalgaeval.dev.config.Encrypt;
import com.dovalgaeval.dev.config.Salt;
import com.dovalgaeval.dev.domain.Member;
import com.dovalgaeval.dev.repository.MemberRepository;
import com.dovalgaeval.dev.request.MemberCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private Encrypt encrypt;

    @BeforeEach
    void clean(){
        memberRepository.deleteAll();
    }

    @Test
    @DisplayName("회원가입")
    void save() {
        //given
        MemberCreate member = MemberCreate.builder()
                .userName("회원1")
                .password("1234")
                .build();

        //when
        memberService.save(member);

        //then
        assertEquals(memberRepository.findAll().get(0).getUserName(),member.getUserName());

    }

    @Test
    @DisplayName("Member userName 암호화 테스트")
    void encrypt(){
        //given
        MemberCreate memberCreate = MemberCreate.builder()
                .userName("이메일")
                .password("1234")
                .build();

        //when
        memberService.save(memberCreate);

        //then
        assertEquals(memberRepository.findAll().get(0).getUserName()
                ,encrypt.getEncrypt(memberCreate.getUserName()));

    }
}