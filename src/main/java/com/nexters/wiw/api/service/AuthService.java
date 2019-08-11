package com.nexters.wiw.api.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private static final String ACCESS_EXPIRE_TIME = "${spring.jwt.accessExpireTime}";
    private static final String REFRESH_EXPIRE_TIME = "${spring.jwt.refreshExpireTime}";

    @Value(JWT_SECRET)
    private String secret;

    @Value(JWT_ISSUER)
    private String issuer;
    
    @Value(ACCESS_EXPIRE_TIME)
    private String accessExpireMinute;

    @Value(REFRESH_EXPIRE_TIME)
    private String refreshExpireMinute;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public Map<String, String> login(final LoginReqeustDto loginDto) {
        String email = loginDto.getEmail();
        userRepository.findByEmail(email).filter(u -> u.matchPassword(loginDto, bCryptPasswordEncoder))
                .orElseThrow(UserNotMatchException::new);

        log.debug(email);
        log.debug(issuer);
        log.debug(accessExpireMinute);

        String accessToken = createAccessToken(email);
        String refreshToken = createRefreshToken(email);

        Map<String, String> tokenMap = new HashMap<String, String>();
        tokenMap.put("accessToken", accessToken);
        tokenMap.put("refreshToken", refreshToken);
        return tokenMap;
    }

    public String createAccessToken(String email) {
        final String type = "ACCESS_TOKEN";
        LocalDateTime expiredTime = LocalDateTime.now().plusMinutes(Integer.parseInt(accessExpireMinute));

        return createToken(email, type, expiredTime);
    }

    public String createRefreshToken(String email) {
        final String type = "REFRESH_TOKEN";
        LocalDateTime expiredTime = LocalDateTime.now().plusDays(Integer.parseInt(refreshExpireMinute));

        return createToken(email, type, expiredTime);
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

    private String createToken(String email, String type, LocalDateTime expireTime) {
        if (email == null || email.isEmpty())
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("role", "REFRESH_TOKEN");

        String token = Jwts.builder().setClaims(claims).setIssuer(issuer)
                .setIssuedAt(DateUtils.convertToDate(currentTime)).setExpiration(DateUtils.convertToDate(expireTime))
                .signWith(SignatureAlgorithm.HS256, generateKey()).compact();

        return token;
    }

}
