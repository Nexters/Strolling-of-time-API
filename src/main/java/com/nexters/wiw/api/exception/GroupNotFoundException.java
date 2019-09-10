package com.nexters.wiw.api.exception;

import com.nexters.wiw.api.domain.error.ErrorType;

public class GroupNotFoundException extends NotFoundException {

    public GroupNotFoundException() {
        super(ErrorType.NOT_FOUND, "GROUP_DOES_NOT_EXIST");
    }
}
