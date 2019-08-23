package com.nexters.wiw.api.ui;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private int id;

    private String nickname;

    private String email;

    private String profileImage;

    private LocalDateTime createdTime;

}