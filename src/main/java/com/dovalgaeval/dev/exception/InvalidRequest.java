package com.dovalgaeval.dev.exception;

import lombok.Getter;

/**
*
* InvalidRequest의 400 에러  Class
* Status -> 400
*
* @author LJH
* 작성일 2022-06-28
**/
@Getter
public class InvalidRequest extends GlobalException{

    private static final String MESSAGE =  "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
