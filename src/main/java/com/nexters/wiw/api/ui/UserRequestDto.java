package com.nexters.wiw.api.ui;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.nexters.wiw.api.domain.User;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequestDto {

    @NotBlank
    private String nickname;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 15)
    private String password;

    private String profileImage;
    
    public User toEntity(PasswordEncoder bCryptPasswordEncoder) {
        
        return User.builder()
            .email(email)
            .nickname(nickname)
            .password(bCryptPasswordEncoder.encode(password)) 
            .profileImage(profileImage)
            .build();
    }

}