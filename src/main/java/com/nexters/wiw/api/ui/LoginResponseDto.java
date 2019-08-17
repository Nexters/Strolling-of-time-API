package com.nexters.wiw.api.ui;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private String token;
    private String tokenType;
    private int expiresIn;

    public LoginResponseDto(String token, String tokenType, int expiresIn) {
        this.token = token;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
    }

}