package com.nexters.wiw.api.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Date;
import java.util.StringTokenizer;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.BadRequestException;
import com.nexters.wiw.api.exception.ExpiredTokenException;
import com.nexters.wiw.api.exception.NotValidTokenException;
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
import io.jsonwebtoken.ExpiredJwtException;
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
        if (authHeader == null || !authHeader.startsWith("Basic ")) {
            throw new BadRequestException(ErrorType.BAD_REQUEST, "잘못된 형식의 인증 요청입니다.");
        }

        String[] basicAuth = authHeader.split("Basic ");
        Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(basicAuth[1]);
        String decodedString = new String(decodedBytes);

        StringTokenizer stringTokenizer = new StringTokenizer(decodedString, ":");

        String email = stringTokenizer.nextToken();
        String password = stringTokenizer.nextToken();

        LoginReqeustDto loginDto = new LoginReqeustDto(email, password);
        User user = userRepository.findByEmail(email).filter(u -> u.matchPassword(loginDto, bCryptPasswordEncoder))
                .orElseThrow(() -> new UnAuthenticationException(ErrorType.UNAUTHENTICATED, "아이디나 비밀번호가 일치하지 않습니다."));

        String token = createToken(user);

        return new LoginResponseDto(token, JWT_TYPE, EXPIRE_IN);
    }

    public String createToken(User user) {
        LocalDateTime expireTime = LocalDateTime.now().plusHours(EXPIRE_IN);

        String email = user.getEmail();
        String nickname = user.getNickname();

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
            .setSubject(email)
            .claim("nickname", nickname)
            .setIssuer(issuer)
            .setIssuedAt(DateUtils.convertToDate(currentTime))
            .setExpiration(DateUtils.convertToDate(expireTime))
            .signWith(SignatureAlgorithm.HS256, generateKey()).compact();

        return token;
    }

    public boolean isValidateToken(final String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new BadRequestException(ErrorType.BAD_REQUEST, "유효하지 않은 토큰 인증 요청입니다.");
        }

        try {
            String splitedToken = token.split("Bearer ")[1];

            Jws<Claims> jws = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(splitedToken);
            String email = jws.getBody().getSubject();
            Date expireDate = jws.getBody().getExpiration();

            return isExistedUser(email) && !isTokenExpired(expireDate);
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException(ErrorType.UNAUTHORIZED, "만료된 토큰입니다.");
        } catch (Exception e) {
            throw new NotValidTokenException(ErrorType.UNAUTHORIZED, "유효하지 않은 형식의 토큰입니다.");
        }

    }

    public Long findIdByToken(String token) {
        String splitedToken = token.split("Bearer ")[1];

        String email = decodeToken(splitedToken);
        return userRepository.findByEmail(email).get().getId();
    }

    private String decodeToken(String token) {
        String splitedToken = token.split("Bearer ")[1];

        Jws<Claims> jws = Jwts.parser().setSigningKey(generateKey()).parseClaimsJws(splitedToken);
        String email = jws.getBody().getSubject();

        return email;
    }

    private boolean isTokenExpired(Date expireDate) {
        final LocalDateTime expiration = DateUtils.convertToLocalDateTime(expireDate);
        return expiration.isBefore(LocalDateTime.now());
    }

    private boolean isExistedUser(String email) {
        return userRepository.findByEmail(email).isPresent();
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
