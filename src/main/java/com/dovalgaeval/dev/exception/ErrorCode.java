package com.dovalgaeval.dev.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
*
* ErrorCode의 JWT 검증할 때 발생할 수 있는 에러코드를 정리해놓음
*
* @author LJH
* 작성일 2022-07-12
**/
@Getter
@AllArgsConstructor
public enum ErrorCode {
    EXPIRED_TOKEN("1001","만료된 토큰입니다."),
    INVALID_TOKEN("1002","잘못된 JWT 토큰입니다."),
    INVALID_SIGNATURE("1003","잘못된 JWT 서명입니다."),
    UNSUPPORTED_TOKEN("1004","지원되지 않는 jwt 토큰"),
    UNKNOWN_TOKEN("1005","토큰이 존재하지 않습니다.");

    private String statueCode;
    private String message;

}
