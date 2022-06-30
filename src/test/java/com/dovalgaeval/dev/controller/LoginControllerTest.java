package com.dovalgaeval.dev.controller;

import com.dovalgaeval.dev.component.Encrypt;
import com.dovalgaeval.dev.domain.Member;
import com.dovalgaeval.dev.repository.MemberRepository;
import com.dovalgaeval.dev.request.MemberCreate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Encrypt encrypt;

    @BeforeEach
    void clean(){
        memberRepository.deleteAll();
    }

    @Test
    @WithAnonymousUser //security 적용후 테스트를 하기 위해서 익명의 사용자를 만들어줘야함
    @DisplayName("회원가입")
    void postRegister() throws Exception {
        //given
        MemberCreate requestMember = MemberCreate.builder()
                .userName("이메일")
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(requestMember);

        //when
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());

        //then
        assertEquals(1L,memberRepository.count());

        Member member = memberRepository.findAll().get(0);
        assertEquals("이메일",encrypt.decryptAES256(member.getUserName()));

    }

    @Test
    @WithAnonymousUser //security 적용후 테스트를 하기 위해서 익명의 사용자를 만들어줘야함
    @DisplayName("회원가입시 userName은 필수입니다.")
    void titleValidation() throws Exception {
        //given
        MemberCreate requestMember = MemberCreate.builder()
                .password("1234")
                .build();

        String json = objectMapper.writeValueAsString(requestMember);

        //when
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andDo(print());

    }
}