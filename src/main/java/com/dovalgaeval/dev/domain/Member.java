package com.dovalgaeval.dev.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

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
public class Member implements UserDetails{

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

    /**
     * getAuthorities : 계정의 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return role.toString();
            }
        });

        return null;
    }

    /**
     * getUsername : 계정 고유 IP
     */
    @Override
    public String getUsername() {
        return userName;
    }

    /**
     * isAccountNonExpired : 계정 만료 여부
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * isAccountNonLocked : 계정 잠금 여부
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * isCredentialsNonExpired 비밀번호 만료 여부
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * isEnabled : 계정 활성화 여부
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
