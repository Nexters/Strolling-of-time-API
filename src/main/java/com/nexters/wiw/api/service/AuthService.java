package com.nexters.wiw.api.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

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

    private static final String JWT_SECRET = "${spring.jwt.secret}";
    
    @Value(JWT_SECRET)
    static private String SECRET;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Map<String, String> login(final LoginReqeustDto loginDto) {
        String email = loginDto.getEmail();
        userRepository.findByEmail(email).filter(u -> u.matchPassword(loginDto, bCryptPasswordEncoder))
                .orElseThrow(UserNotMatchException::new);

        String accessToken = createAccessToken(email);
        String refreshToken = createRefreshToken(email);

        Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);
        return tokenMap;
    }

    public String createToken(final String email) {
        String jwt = Jwts.builder().setHeaderParam("typ", "JWT").setHeaderParam("regDate", System.currentTimeMillis())
                .signWith(SignatureAlgorithm.HS256, generateKey()).claim("email", email).compact();

        return jwt;
    }

    public String createAccessToken(String email) {
        return null;
    }

    public String createRefreshToken(String email) {
        return null;
    }

    public String decodeToken(String token) {
        return Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token).getBody().get("email").toString();
    }

    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = JWT_SECRET.getBytes("UTF-8");
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
