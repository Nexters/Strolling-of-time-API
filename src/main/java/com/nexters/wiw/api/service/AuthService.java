package com.nexters.wiw.api.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Base64.Decoder;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.BadRequestException;
import com.nexters.wiw.api.exception.UnAuthenticationException;
import com.nexters.wiw.api.ui.LoginReqeustDto;
import com.nexters.wiw.api.ui.LoginResponseDto;
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

    private static final String JWT_SECRET = "${spring.jwt.secret}";
    private static final String JWT_ISSUER = "${spring.jwt.issuer}";
    private static final String JWT_TYPE = "Bearer";
    private static final int EXPIRE_IN = 24;

    @Value(JWT_SECRET)
    private String secret;

    @Value(JWT_ISSUER)
    private String issuer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Transactional
     public LoginResponseDto login(final String authHeader) {
        if (!authHeader.startsWith("Basic ")){
            throw new BadRequestException(ErrorType.BAD_REQUEST, "required basic auth");
        }

        String[] basicAuth = authHeader.split("Basic ");
        Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(basicAuth[1]);
        String decodedString = new String(decodedBytes);

        StringTokenizer stringTokenizer = new StringTokenizer(decodedString, ":");

        String email = stringTokenizer.nextToken();
        String password = stringTokenizer.nextToken();

        LoginReqeustDto loginDto = new LoginReqeustDto(email, password);
        userRepository.findByEmail(email).filter(u -> u.matchPassword(loginDto, bCryptPasswordEncoder))
                 .orElseThrow(() -> new UnAuthenticationException(ErrorType.UNAUTHENTICATED, "UNAUTHENTICATED"));

        String token = createToken(email);

        return new LoginResponseDto(token, JWT_TYPE, EXPIRE_IN);
    }

    public String createToken(String email) {
        LocalDateTime expireTime = LocalDateTime.now().plusHours(EXPIRE_IN);

        if (email.isEmpty())
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(email);

        String token = Jwts.builder().setClaims(claims).setIssuer(issuer)
                .setIssuedAt(DateUtils.convertToDate(currentTime)).setExpiration(DateUtils.convertToDate(expireTime))
                .signWith(SignatureAlgorithm.HS256, generateKey()).compact();

        return token;
    }

    public String decodeToken(String token) {
        Jws<Claims> jws = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token);
        String email = jws.getBody().getSubject();

        return email;
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

    public boolean isValidateToken(String token, User user) {
        Jws<Claims> jws = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(token);
        String email = jws.getBody().getSubject();
        Date expireDate = jws.getBody().getExpiration();

        return (email.equals(user.getEmail()) && !isTokenExpired(expireDate));
    }

    private boolean isTokenExpired(Date expireDate) {
        final LocalDateTime expiration = DateUtils.convertToLocalDateTime(expireDate);
        return expiration.isBefore(LocalDateTime.now());
    }
}
