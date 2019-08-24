package com.nexters.wiw.api.ui;

import javax.validation.constraints.NotBlank;

import com.nexters.wiw.api.domain.User;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPatchRequestDto {

    @NotBlank
    private String nickname;

    private String profileImage;
    
    public User toEntity(PasswordEncoder bCryptPasswordEncoder) {
        
        return User.builder()
            .nickname(nickname)
            .profileImage(profileImage)
            .build();
    }

}