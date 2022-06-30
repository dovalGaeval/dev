package com.dovalgaeval.dev.component;

import com.dovalgaeval.dev.exception.InvalidRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
*
* Encrypt의 암호화를 위한 class
* userName 양방향 암호화(AES-256)
*
* @author LJH
* 작성일 2022-06-28
* 수정일 2022-06-29 양방향암호화 추가, spring security 적용으로 단방향 암호화 삭제
**/
@Component
public class Encrypt {

    @Value("${encrypt.alg}")
    private String alg; //암호화 알고리즘

    @Value("${encrypt.key}")
    private String key; //key
    @Value("${encrypt.iv}")
    private String iv; // 초기화벡터

    /**
     *
     * encryptAES256 암호화
     *
     * @param userName
     * @return 암호화된 userName
     */
    public String encryptAES256(String userName){
        String encryptUserName = null;
        try {
            Cipher cipher = Cipher.getInstance(alg); //Cipher클래스는 암호화, 복화화 기능을 제공함
            //alg으로 초기화된 Cipher객체
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8),"AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            //Cipher.ENCRYPT_MODE : cipher 객체를 암호화 모드로 초기화한다.
            //Cipher.DECRYPT_MODE : cipher 객체를 복호화 모드로 초기화한다.
            //Cipher.WRAP_MODE : cipher 객체를 key-wrapping 모드로 초기화한다.
            //Cipher.UNWRAP_MODE : cipher 객체를 key-unwrapping 모드로 초기화한다.
            byte[] encrypted  = cipher.doFinal(userName.getBytes(StandardCharsets.UTF_8));
            encryptUserName = Base64.getEncoder().encodeToString(encrypted);
        }catch (Exception e) {
            throw new InvalidRequest();
        }

        return encryptUserName;
    }

    /**
     *
     * decryptAES256 복호화
     *
     * @param encryptUserName 암호화된 userName
     * @return 복호화된 userName
     */
    public String decryptAES256(String encryptUserName){

        String decrypt = null;

        try {
            Cipher cipher = Cipher.getInstance(alg); //Cipher클래스는 암호화, 복화화 기능을 제공함
            //alg으로 초기화된 Cipher객체
            SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8),"AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);

            byte[] decode = Base64.getDecoder().decode(encryptUserName);
            byte[] decrypted  = cipher.doFinal(decode);
            decrypt = new String(decrypted, "UTF8");

        }catch (Exception e){
            throw new InvalidRequest();
        }

        return decrypt;
    }

}
