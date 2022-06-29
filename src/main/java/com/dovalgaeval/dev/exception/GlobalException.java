package com.dovalgaeval.dev.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
*
* GlobalException
*
* @author LJH
* 작성일 2022-06-28
**/
@Getter
public abstract class GlobalException extends RuntimeException{

    public Map<String, String> validation;

    public GlobalException(String message) {
        super(message);
    }

    public GlobalException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message){
        validation = new HashMap<>();
        validation.put(fieldName,message);
    }
}
