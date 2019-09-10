package com.nexters.wiw.api.exception;

import com.nexters.wiw.api.domain.error.ErrorType;

public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException() {
        super(ErrorType.NOT_FOUND, "USER_DOES_NOT_EXIST");
    }
}
