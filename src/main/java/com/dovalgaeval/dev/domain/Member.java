package com.dovalgaeval.dev.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
*
* Member 회원 entity
*
* @author LJH
* 작성일 2022-06-27
**/
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키 생성을 데이터베이스에 위임함
    private Long id;
    private String userName;
    private String password;

    @Builder
    public Member(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

}
