package com.dovalgaeval.dev.component;

import com.dovalgaeval.dev.domain.Member;
import com.dovalgaeval.dev.service.MemberService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
*
* JwtTokenProvider의 jwt token 제공을 위한 class
*
* @author LJH
* 작성일 2022-07-03
**/
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("jwt.key")
    private String jwtKey;

    @Value("jwt.effective.time")
    private String jwtEffectiveTime;

    private MemberService memberService;
    /**
     *
     * validateToken token 검증
     *
     * @param token
     * @return true/false
     */
    public boolean validateToken(String token) {
        try{
            Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            log.error("Invalid JWT signature");
        }catch (MalformedJwtException e){
            log.error("Invalid JWT token");
        }catch (ExpiredJwtException e){
            log.error("Expried JWT token");
        }catch (UnsupportedJwtException e){
            log.error("Unsupported JWT token");
        }catch (IllegalArgumentException e){
            log.error("JWT claims string is empty");
        }

        return false;
    }


    /**
     *
     * createToken : Authentication 객체로 Token 생성
     *
     * @param authentication 인증된 객체
     * @return token
     */
    public String createToken(Authentication authentication){
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + Integer.parseInt(jwtEffectiveTime));

        return Jwts.builder()
                .setSubject(String.valueOf(authentication.getPrincipal())) //authentication.getPrincipal -> ID
                .setIssuedAt(now)           //현재 시간 기반으로 생성
                .setExpiration(expireDate)  //만료 시간 세팅
                .signWith(SignatureAlgorithm.HS512, jwtKey)  //사용할 암호화 알고리즘, key값
                .compact();
    }

    /**
     *
     * getUserIdFromJWT token으로부터 Claims를 만들고 -> userName
     *
     * @param token
     * @return userName
     */
    public Authentication getUserIdFromJWT(String token) {
        String userName = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody().getSubject();

        Member member = memberService.loadUserByUsername(userName);

        return new UsernamePasswordAuthenticationToken(member.getUsername(),null,member.getAuthorities());

    }

    /**
     *
     * getJwtFromRequest 헤더로 부터 bearer 토큰을 가져옴
     *
     * @param request
     * @return token
     */
    public String getJwtFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.isNotEmpty(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring("Bearer ".length());
        }
        return null;
    }
}
