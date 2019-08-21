package com.nexters.wiw.api.ui;

import java.util.Collection;

import org.springframework.hateoas.PagedResources.PageMetadata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPageListDto {

    private Collection<UserResponseDto> users;

    private PageMetadata PageMetadata;

    public UserPageListDto(Collection<UserResponseDto> users, PageMetadata pageMetadata) {
        this.users = users;
        this.PageMetadata = pageMetadata;
    }
}