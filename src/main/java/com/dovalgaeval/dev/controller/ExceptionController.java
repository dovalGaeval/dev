package com.dovalgaeval.dev.controller;

import com.dovalgaeval.dev.exception.GlobalException;
import com.dovalgaeval.dev.response.ErrorResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
*
* ExceptionController Controller에서 발생하는 에러들을 관리하는 class
* ControllerAdvice : 모든 @Controller 즉, 전역에서 발생할 수 있는 예외를 잡아 처리해주는 annotation
*
* @author LJH
* 작성일 2022-06-28
**/
@ControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e){
        ErrorResponse response = ErrorResponse.builder()
                .code("400").message("잘못된 요청입니다.").build();

        List<FieldError> fieldErrors = e.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            response.addValidation(fieldError.getField(),fieldError.getDefaultMessage());
        }

        return response;
    }

}
