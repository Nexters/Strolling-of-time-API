package com.nexters.wiw.api.exceptions;

import com.nexters.wiw.api.domain.error.ErrorType;

public class DuplicationException extends ErrorEntityException {

    public DuplicationException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}
