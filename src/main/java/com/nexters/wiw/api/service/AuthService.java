package com.nexters.wiw.api.service;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.exception.UserNotMatchException;
import com.nexters.wiw.api.ui.LoginReqeustDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {
    // private static final String JWT_SECRET = "${jwt.secret}";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    // @Value(JWT_SECRET)
    static private String SECRET = "24wq5uei12uewdq1po2iewodksa";

    @Transactional
    public String login(final LoginReqeustDto loginDto) {
        String email = loginDto.getEmail();
        userRepository.findByEmail(email)
            .filter(u -> u.matchPassword(loginDto, bCryptPasswordEncoder))
            .orElseThrow(UserNotMatchException::new);

        return createToken(email);
    }

    public String createToken(final String email) {
        String jwt = Jwts.builder()
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("regDate", System.currentTimeMillis())
            .signWith(SignatureAlgorithm.HS256, generateKey())
            .claim("email", email)
            .compact();

        return jwt;
    }

    public String decodeToken(String token) {
        return Jwts.parser()
            .setSigningKey(generateKey())
            .parseClaimsJws(token)
            .getBody().get("email").toString();
    }

    public byte[] generateKey() {
        byte[] key = null;
        try {
            key = SECRET.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            if (log.isInfoEnabled()) {
                e.printStackTrace();
            } else {
                log.error("Making JWT Key Error ::: {}", e.getMessage());
            }
        }
        return key;
    }

}
