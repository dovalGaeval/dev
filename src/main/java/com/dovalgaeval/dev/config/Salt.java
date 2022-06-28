package com.dovalgaeval.dev.config;

import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
*
* Salt : salt 생성을 위한 class
*
* @author LJH
* 작성일 2022-06-28
**/
@Configuration
public class Salt {

    public String getSalt(){

        String salt = null;

        try {
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            byte[] bytes = new byte[16];

            secureRandom.nextBytes(bytes);

            salt = new String(Base64.getEncoder().encode(bytes));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        return salt;
    }


}
