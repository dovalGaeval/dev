package com.dovalgaeval.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
*
* LoginController의 설명 : 로그인을 위한 controller
*
* @author LJH
* 작성일 2022-06-26
**/
@Controller
public class LoginController {

    @GetMapping("/")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String register(){
        return "register";
    }

}
