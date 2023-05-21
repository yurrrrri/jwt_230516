package com.ll.jwt;

import com.ll.jwt.base.jwt.JwtProvider;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;

import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JwtTests {

    @Autowired
    private JwtProvider jwtProvider;

    @Value("${custom.jwt.secretKey}")
    private String secretKeyPlain;

    @Test
    @DisplayName("secretKey 키가 존재해야 한다.")
    void t001() {
        assertThat(secretKeyPlain).isNotNull();
    }

    @Test
    @DisplayName("secretKey 원문으로 hmac 암호화 알고리즘에 맞는 SecretKey 객체를 만들 수 있다.")
    void t002() {
        String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
        SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
        assertThat(secretKey).isNotNull();
    }

    @Test
    @DisplayName("JwtProvider 객체로 SecretKey 객체를 생성할 수 있다.")
    void t003(){
        SecretKey secretKey = jwtProvider.getSecretKey();
        assertThat(secretKey).isNotNull();
    }

    @Test
    @DisplayName("SecretKey 객체는 단 한 번 생성되어야 한다.")
    void t004(){
        SecretKey secretKey1 = jwtProvider.getSecretKey();
        SecretKey secretKey2 = jwtProvider.getSecretKey();

        assertThat(secretKey1 == secretKey2).isTrue();
    }

}