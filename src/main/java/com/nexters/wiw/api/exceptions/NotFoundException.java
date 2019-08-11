package com.nexters.wiw.api.exceptions;

import com.nexters.wiw.api.domain.error.ErrorType;

public class NotFoundException extends ErrorEntityException {

    public NotFoundException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
