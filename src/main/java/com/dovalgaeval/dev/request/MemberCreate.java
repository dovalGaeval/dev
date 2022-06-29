package com.dovalgaeval.dev.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
*
* MemberCreate 회원가입시 validation check를 위한 class
*
* @author LJH
* 작성일 2022-06-27
**/
@Data
public class MemberCreate {

    @NotBlank(message = "이메일을 입력해주세요")
    private String userName;
    @NotBlank(message = "패스워드를 입력해주세요")
    private String password;


    @Builder
    public MemberCreate(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

}
