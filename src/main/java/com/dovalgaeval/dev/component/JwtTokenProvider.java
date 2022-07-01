package com.dovalgaeval.dev.component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("jwt.key")
    private String jwtKey;

    @Value("jwt.effective.time")
    private int jwtEffectiveTime;

    public String createToken(Authentication authentication){
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + jwtEffectiveTime);

        return Jwts.builder()
                .setSubject(String.valueOf(authentication.getPrincipal())) //authentication.getPrincipal -> ID
                .setIssuedAt(now)           //현재 시간 기반으로 생성
                .setExpiration(expireDate)  //만료 시간 세팅
                .signWith(SignatureAlgorithm.HS512, jwtKey)  //사용할 암호화 알고리즘, key값
                .compact();
    }

}
