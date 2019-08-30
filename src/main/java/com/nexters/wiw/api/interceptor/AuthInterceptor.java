package com.nexters.wiw.api.interceptor;

import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.ImmutableMap;
import com.nexters.wiw.api.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Optional;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    // TODO: Spring Security filter
    static final Map<String, String> excludeRequest = ImmutableMap.of("/api/v1/users", "POST");

    @Autowired
    private AuthService authService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (isExcludeRequest(request))
            return true;

        String authHeader = request.getHeader(AuthService.HEADER_AUTH);

        if (authHeader == null)
            authHeader = "";

        Long userId = authService.getUserIdByAuthHeader(authHeader);
        if (0 < userId) {
            request.setAttribute("userId", userId);
            return true;
        }

        HttpStatus status = HttpStatus.UNAUTHORIZED;
        response.setStatus(status.value());
        response.setContentType("text/plain");
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Writer writer = response.getWriter();
        writer.write("인증이 필요합니다");
        writer.close();
        return false;
    }

    private Boolean isExcludeRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        Optional<String> method = Optional.ofNullable(excludeRequest.get(requestURI));
        return method.orElse("").equals(httpMethod);
    }
}