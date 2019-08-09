package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.domain.User;

public class UserRequestDto {
    private String name;

    public User of() {
        return User.builder().name(this.name).build();
    }
}
