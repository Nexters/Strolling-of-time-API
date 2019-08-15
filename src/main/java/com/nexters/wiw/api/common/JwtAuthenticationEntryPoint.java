package com.nexters.wiw.api.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //인증을 포함하지 않을 채로 요청을 보냈을 때 오류 코드 반환
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException {
        
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());


        // 500에러가 떴을 때 response를 활용해서 예외를 던지도록
    }
}