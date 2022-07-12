package com.dovalgaeval.dev.component;

import com.dovalgaeval.dev.exception.ErrorCode;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
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
public class JwtTokenProvider {

    @Value("${jwt.key}")
    private String jwtKey;

    @Value("${jwt.effective.time}")
    private int jwtEffectiveTime;

    /**
     *
     * validateToken token 검증
     *
     * @param token
     * @return true/false
     */
    public boolean validateToken(String token,HttpServletRequest request) {
        try{
            Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token);
            return true;
        }catch (SignatureException e){
            request.setAttribute("exception", ErrorCode.INVALID_SIGNATURE.getStatueCode());
        }catch (MalformedJwtException e){
            request.setAttribute("exception", ErrorCode.INVALID_TOKEN.getStatueCode());
        }catch (ExpiredJwtException e){
            request.setAttribute("exception", ErrorCode.EXPIRED_TOKEN.getStatueCode());
        }catch (UnsupportedJwtException e){
            request.setAttribute("exception", ErrorCode.UNSUPPORTED_TOKEN.getStatueCode());
        }catch (IllegalArgumentException e){
            request.setAttribute("exception", ErrorCode.UNKNOWN_TOKEN.getStatueCode());
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

        Date expireDate = new Date(now.getTime() + jwtEffectiveTime);

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
    public String getUserIdFromJWT(String token) {

        Claims claims = Jwts.parser().setSigningKey(jwtKey).parseClaimsJws(token).getBody();

        return claims.getSubject();

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
