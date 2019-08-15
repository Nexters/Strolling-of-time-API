package com.nexters.wiw.api.web;


import com.nexters.wiw.api.service.AuthService;
import com.nexters.wiw.api.ui.LoginReqeustDto;
import com.nexters.wiw.api.ui.LoginResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    //최초 로그인할 때 토큰을 발급
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody final LoginReqeustDto loginRequestDto) {
        LoginResponseDto loginResponseDto = authService.login(loginRequestDto);
        return new ResponseEntity<LoginResponseDto>(loginResponseDto, HttpStatus.OK);
    }
}
