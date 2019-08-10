package com.nexters.wiw.api.aop;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import com.nexters.wiw.api.exception.UnauthorizedException;
import com.nexters.wiw.api.service.AuthService;
import com.nexters.wiw.api.util.DateUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;

@Aspect
@Component
public class AuthAspect {
    // (현재시간 - 가장 최근 api 요청 시간 > 토큰 재발급 시간(5분)) 비활성화된 유저라고 판단.

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private AuthService authService;
    
    @Around("@annotation(Auth)")
    public Object isUseableJWT(final ProceedingJoinPoint joinPoint) throws Throwable {

        final String token = httpServletRequest.getHeader("Authorization");

        try {
            //accesstoken을 해독
            Claims claims = authService.decodeToken(token);

            String email = claims.getSubject();
            LocalDateTime expireDate = DateUtils.convertToLocalDateTime(claims.getExpiration());

            //accesstoken의 만기일이 지났을 떄 refreshtoken을 활용해서 다시 인증하도록
            if(expireDate.isBefore(LocalDateTime.now())) {
                // TODO refreshToken을 요청하고 만료기간 확인
                // 클라이언트에 요청을 어떻게 할지?
            }
            
            // api 요청시마다 accesstoken 재발급?

            return joinPoint.proceed(joinPoint.getArgs());
        } catch (Exception e) {
            //토큰의 형식이 맞지 않을 경우
            throw new UnauthorizedException();
        }

    }

}