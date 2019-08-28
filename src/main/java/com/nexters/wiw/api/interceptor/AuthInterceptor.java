package com.nexters.wiw.api.interceptor;

import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.UnAuthorizedException;
import com.nexters.wiw.api.service.AuthService;
import com.nexters.wiw.api.ui.LoginResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        final String authHeader = request.getHeader(AuthService.HEADER_AUTH);
        if (authHeader == null) {
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "Authorization이 없습니다.");
        } else if (authHeader.startsWith(AuthService.BASIC_AUTH_KEY)) {
            final String basicAuth = authHeader.split(AuthService.BASIC_AUTH_KEY)[1];
            String decodedString = AuthService.decodeBasicAuth(basicAuth);

            StringTokenizer stringTokenizer = new StringTokenizer(decodedString, ":");

            String email = stringTokenizer.nextToken();
            String password = stringTokenizer.nextToken();

            User user = authService.login(email, password);
            request.setAttribute("userId", user.getId());
        } else if (authHeader.startsWith(AuthService.TOKEN_AUTH_KEY)) {
            final String token = authHeader.split(AuthService.TOKEN_AUTH_KEY)[1];
            if (!authService.isValidateToken(token)) {
                throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "유효하지 않은 토큰 인증 요청입니다.");
            }
            Long userId = authService.findIdByToken(token);
            request.setAttribute("userId", userId);
        } else {
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "유효하지 않은 토큰 인증 요청입니다.");
        }

        return true;
    }
}