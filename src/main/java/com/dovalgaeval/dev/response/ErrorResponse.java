package com.dovalgaeval.dev.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
*
* ErrorResponse
* {
*     "code" : "400",
*     "message" : "잘못된 요청입니다."
*     "validation": {
*         "title" : "제목을 입력해주세요",
*         "content" : "내용을 입력해주세요"
*     }
* }
* @author LJH
* 작성일 2022-06-28
**/


@Getter
@RequiredArgsConstructor
public class ErrorResponse {

    private final String code;
    private final String message;

    private Map<String,String> validation;

    public void addValidation(String field,String defaultMessage){
        validation = new HashMap<>();
        this.validation.put(field,defaultMessage);
    }

    @Builder
    public ErrorResponse(String code, String message, Map<String, String> validation) {
        this.code = code;
        this.message = message;
        this.validation = validation;
    }
}
