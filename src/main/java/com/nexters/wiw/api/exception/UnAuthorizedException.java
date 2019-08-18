package com.nexters.wiw.api.exception;

import com.nexters.wiw.api.domain.error.ErrorType;

public class UnAuthorizedException extends ErrorEntityException {

    private static final long serialVersionUID = 1L;

    public UnAuthorizedException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
