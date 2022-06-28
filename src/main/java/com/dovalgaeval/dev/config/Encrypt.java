package com.dovalgaeval.dev.config;

import com.dovalgaeval.dev.domain.Member;
import org.springframework.context.annotation.Configuration;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
*
* Encrypt의 암호화를 위한 class
*
* @author LJH
* 작성일 2022-06-28
**/
@Configuration
public class Encrypt {

    public String getEncrypt(String raw){

        String encrypt = null;
        //암호화
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            md.update(raw.getBytes(StandardCharsets.UTF_8));

            encrypt = String.format("%128x", new BigInteger(1, md.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return encrypt;
    }

}
