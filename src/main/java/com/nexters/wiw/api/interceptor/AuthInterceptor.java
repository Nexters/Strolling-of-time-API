package com.nexters.wiw.api.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.UnAuthorizedException;
import com.nexters.wiw.api.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor{
    private static final String HEADER_AUTH = "Authorization";
 
    @Autowired
    private AuthService authService;
 
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        final String token = request.getHeader(HEADER_AUTH);

        if(!authService.isValidateToken(token)) {
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "유효하지 않은 토큰 인증 요청입니다.");
        }
        return true;
    }
}