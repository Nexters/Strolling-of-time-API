package com.nexters.wiw.api.aop;

import javax.servlet.http.HttpServletRequest;

import com.nexters.wiw.api.exception.UnauthorizedException;
import com.nexters.wiw.api.service.AuthService;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;

@Aspect
@Component
public class AuthAspect {
    //토큰을 받아서 토큰 사용 가능여부 반환 + 기간이 만료됐으면 토큰 재발급 로직
    //이메일을 뽑아 어떤 회원인지를 구별 -> 이걸 어디서 활용해야 하지..?

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private AuthService authService;
    
    @Around("@annotation(Auth)")
    public Object isUseableJWT(final ProceedingJoinPoint joinPoint) throws Throwable {
        
        final String token = httpServletRequest.getHeader("Authorization");
        String email = "";

        try {
            email = authService.decodeToken(token);
            return joinPoint.proceed(joinPoint.getArgs());
        } catch(ExpiredJwtException e) {
            // TODO : refreshToken을 통해 인증 후 토큰을 재발급.
            String refreshToken = authService.createToken(email);
            return new ResponseEntity<String>(refreshToken, HttpStatus.OK);
        } catch (Exception e) {
            //토큰의 형식이 맞지 않을 경우
            throw new UnauthorizedException();
        }

    }

}