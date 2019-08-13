package com.nexters.wiw.api.exceptions;

import com.nexters.wiw.api.domain.error.ErrorType;

public class UnAuthenticationException extends ErrorEntityException {

    public UnAuthenticationException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
