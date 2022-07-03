package com.dovalgaeval.dev.service;

import com.dovalgaeval.dev.component.Encrypt;
import com.dovalgaeval.dev.domain.Member;
import com.dovalgaeval.dev.repository.MemberRepository;
import com.dovalgaeval.dev.request.MemberCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private Encrypt encrypt;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        assertEquals(encrypt.decryptAES256(memberRepository.findAll().get(0).getUsername()),member.getUserName());

    }

    @Test
    @DisplayName("Member userName 암호화, 복호화 테스트")
    void encrypt(){
        //given
        MemberCreate memberCreate = MemberCreate.builder()
                .userName("이메일") //userName 암호화
                .password("1234")
                .build();

        //when
        memberService.save(memberCreate);

        //then
        assertEquals(encrypt.decryptAES256(memberRepository.findAll().get(0).getUsername())
                ,memberCreate.getUserName());


    }

    @Test
    @DisplayName("Member password 암호화 테스트")
    void encryptPassword(){
        //given
        MemberCreate memberCreate = MemberCreate.builder()
                .userName("이메일")
                .password("1234")
                .build();

        //when
        memberService.save(memberCreate);

        //then
        assertEquals(encrypt.decryptAES256(memberRepository.findAll().get(0).getUsername())
                ,memberCreate.getUserName());

        assertThat(passwordEncoder.matches(memberCreate.getPassword(),memberRepository.findAll().get(0).getPassword())).isEqualTo(true);
    }


    @Test
    @DisplayName("Member 조회하기")
    void selectMember(){
        //given
        MemberCreate memberCreate = MemberCreate.builder()
                .userName("테스트")
                .password("1234").build();

        memberService.save(memberCreate); //회원가입
        
        //when
        Member memberResponse = memberService.loadUserByUsername("테스트");
        
        //then
        assertEquals(memberResponse.getUsername(),memberCreate.getUserName());
    }

}