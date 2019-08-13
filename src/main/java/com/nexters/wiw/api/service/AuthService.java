package com.nexters.wiw.api.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Date;

import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.exception.UserNotMatchException;
import com.nexters.wiw.api.ui.LoginReqeustDto;
import com.nexters.wiw.api.util.DateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {
    // TODO Db table의 update시간 데이터 수정
    
    private static final String JWT_SECRET = "${spring.jwt.secret}";
    private static final String JWT_ISSUER = "${spring.jwt.issuer}";
    private static final String REFRESH_EXPIRE_TIME = "${spring.jwt.expireTime}";

    @Value(JWT_SECRET)
    private String secret;

    @Value(JWT_ISSUER)
    private String issuer;

    @Value(REFRESH_EXPIRE_TIME)
    private String tokenExpireDay;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public String login(final LoginReqeustDto loginDto) {
        String email = loginDto.getEmail();
        userRepository.findByEmail(email).filter(u -> u.matchPassword(loginDto, bCryptPasswordEncoder))
                .orElseThrow(UserNotMatchException::new);

        String token = createToken(email);

        return token;
    }

    public String createToken(String email) {
        LocalDateTime expireTime = LocalDateTime.now().plusDays(Integer.parseInt(tokenExpireDay));

        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(email);

        String token = Jwts.builder().setClaims(claims).setIssuer(issuer)
                .setIssuedAt(DateUtils.convertToDate(currentTime)).setExpiration(DateUtils.convertToDate(expireTime))
                .signWith(SignatureAlgorithm.HS256, generateKey()).compact();

        return token;
    }

    public Claims decodeToken(String token) {
        Jws<Claims> jws = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token);
        String subject = jws.getBody().getSubject();
        Date expireDate = jws.getBody().getExpiration();

        Claims claims = Jwts.claims().setSubject(subject);
        claims.setExpiration(expireDate);
        return claims;
    }

    private byte[] generateKey() {
        byte[] key = null;
        try {
            key = secret.getBytes("UTF-8");
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
