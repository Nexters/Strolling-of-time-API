package com.nexters.wiw.api.config;

import java.util.List;

import com.nexters.wiw.api.interceptor.AuthInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private static final String[] EXCLUDE_PATHS = {
            // "/api/v1/auth/**",
            "/api/v1/users", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html**", "/webjars/**",
            "favicon.ico", "/static/**", "swagger-ui.html", "/webjars/**", "/v2/api-docs", "/configuration/security",
            "/configuration/ui", "/swagger-resources"

    };

    @Autowired
    private AuthInterceptor authInterceptor;

    @Autowired
    private AuthArgumentResolver resolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(resolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).excludePathPatterns(EXCLUDE_PATHS);
    }
}