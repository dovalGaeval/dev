package com.dovalgaeval.dev.controller;

import com.dovalgaeval.dev.request.MemberCreate;
import com.dovalgaeval.dev.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
*
* LoginController의 설명 : 로그인을 위한 controller
*
* @author LJH
* 작성일 2022-06-26
**/
@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    /**
     *
     * index 로그인페이지 이동
     *
     * @param mv
     * @return 로그인 페이지
     */
    @GetMapping("/login")
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("login");
        return mv;
    }

    /**
     *
     * register 회원가입 페이지
     *
     * @param mv
     * @return mv 회원가입 페이지
     */
    @GetMapping("/register")
    public ModelAndView register(ModelAndView mv){
        mv.setViewName("register");
        return mv;
    }

    /**
     *
     * postRegister 회원가입
     *
     * @param request
     */
    @PostMapping("/register")
    public void postRegister(@RequestBody @Valid MemberCreate request){
        memberService.save(request);
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid MemberCreate request, HttpServletResponse response){
        String jwt = memberService.login(request);
        response.setHeader("Authorization","Bearer " + jwt);
    }
}
