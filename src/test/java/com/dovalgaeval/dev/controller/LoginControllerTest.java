package com.dovalgaeval.dev.controller;

import com.dovalgaeval.dev.component.Encrypt;
import com.dovalgaeval.dev.domain.Member;
import com.dovalgaeval.dev.repository.MemberRepository;
import com.dovalgaeval.dev.request.MemberCreate;
import com.dovalgaeval.dev.service.MemberService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
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

        //expected
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isOk())
                .andDo(print());


        assertEquals(1L,memberRepository.count());

        Member member = memberRepository.findAll().get(0);
        assertEquals("이메일",encrypt.decryptAES256(member.getUsername()));

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

        //expected
        mockMvc.perform(post("/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andDo(print());

    }

    @Test
    @WithAnonymousUser
    @DisplayName("로그인")
    void login() throws Exception {
        //given
        MemberCreate requestMember = MemberCreate.builder()
                .userName("이메일")
                .password("1234")
                .build();

        memberService.save(requestMember); //회원가입

        String json = objectMapper.writeValueAsString(requestMember);

        //expected
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("userName","이메일")
                        .param("password","1234")
                )
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    @WithAnonymousUser
    @DisplayName("로그인시 비밀번호가 틀리면 발생하는 에러 확인")
    void unauthorizedTest() throws Exception {
        //given
        MemberCreate requestMember = MemberCreate.builder()
                .userName("이메일")
                .password("1234")
                .build();

        memberService.save(requestMember); //회원가입

        //expected
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("userName","이메일")
                        .param("password","1234512")
                )
                .andExpect(status().is4xxClientError())
                .andDo(print());

    }

}