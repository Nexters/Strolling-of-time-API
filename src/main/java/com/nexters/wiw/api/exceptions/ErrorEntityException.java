package com.nexters.wiw.api.exceptions;

import com.nexters.wiw.api.domain.error.ErrorEntity;
import com.nexters.wiw.api.domain.error.ErrorType;

public class ErrorEntityException extends RuntimeException {

    protected ErrorType errorType;

    public ErrorEntityException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorEntity entity() {
        return new ErrorEntity(errorType, getMessage());
    }
}
