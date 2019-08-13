package com.nexters.wiw.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
     @Override
     protected void configure(HttpSecurity http) throws Exception {
          http.csrf().disable()
          .authorizeRequests()
          .antMatchers("/admin", "/admin/*").authenticated()
          .anyRequest().permitAll()
          .and()
          .formLogin()
          .loginPage("/login")
          .permitAll()
          .defaultSuccessUrl("/")
          .and()
          .logout()
          .logoutUrl("/logout")
          .logoutSuccessUrl("/")
          .invalidateHttpSession(true)
          .permitAll();
     }

     @Bean
    public PasswordEncoder bCryptpasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}