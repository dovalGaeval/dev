package com.dovalgaeval.dev.controller;

import com.dovalgaeval.dev.domain.Member;
import com.dovalgaeval.dev.request.MemberCreate;
import com.dovalgaeval.dev.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


/**
*
* LoginController의 설명 : 로그인을 위한 controller
*
* @author LJH
* 작성일 2022-06-26
**/
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final MemberService memberService;

    @GetMapping("/")
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("login");
        return mv;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView mv){
        mv.setViewName("register");
        return mv;
    }

    @PostMapping("/register")
    public void postRegister(@RequestBody @Valid MemberCreate request){
        memberService.save(request);
    }

}
