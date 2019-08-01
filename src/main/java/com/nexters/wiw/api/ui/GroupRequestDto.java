package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.domain.Group;

public class GroupRequestDto {
    private String name;

    public Group of() {
        return Group.builder().name(this.name).build();
    }
}