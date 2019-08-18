package com.nexters.wiw.api.ui;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class LoginReqeustDto {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 15)
    private String password;

    @Builder
    public LoginReqeustDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
    
    public static LoginReqeustDto defaultLoginReqeustDto() {
        return LoginReqeustDto.builder().email("daye@nexters.com").password("12341234").build();
    }
}