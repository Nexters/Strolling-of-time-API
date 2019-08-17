package com.nexters.wiw.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable()
                .authorizeRequests()
                .anyRequest().permitAll();
//            .authorizeRequests()
//            .antMatchers(HttpMethod.POST, "/api/v1/auth").permitAll()
//            .antMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
//            .antMatchers(HttpMethod.POST, "/api/v1/**").permitAll()
//            .antMatchers(
//                HttpMethod.GET,
//                "/v2/api-docs",
//                "/swagger-resources/**",
//                "/swagger-ui.html**",
//                "/webjars/**",
//                "favicon.ico").permitAll()
//            .anyRequest().authenticated();

    }

    @Bean
    public PasswordEncoder bCryptpasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}