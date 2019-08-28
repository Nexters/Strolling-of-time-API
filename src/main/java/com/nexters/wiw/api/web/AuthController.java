package com.nexters.wiw.api.web;


import com.nexters.wiw.api.common.Auth;
import com.nexters.wiw.api.service.AuthService;
import com.nexters.wiw.api.ui.LoginResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RequestMapping(value = "/api/v1/auth")
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    //최초 로그인할 때 토큰을 발급
    @PostMapping("")
    @ApiOperation(value = "로그인", authorizations = { @Authorization(value="basicAuth") })
    public ResponseEntity<LoginResponseDto> login(@Auth Long userId){
        String token = authService.createTokenByUserId(userId);
        LoginResponseDto loginResponseDto = new LoginResponseDto(token, AuthService.JWT_TYPE, AuthService.EXPIRE_IN);
        return new ResponseEntity<LoginResponseDto>(loginResponseDto, HttpStatus.OK);
    }
}
