package com.dovalgaeval.dev.domain;

import com.dovalgaeval.dev.Role;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
*
* Member 회원 entity
*
* @author LJH
* 작성일 2022-06-27
* 수정일 2022-06-30 role 추가
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

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String userName, String password,Role role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

}
