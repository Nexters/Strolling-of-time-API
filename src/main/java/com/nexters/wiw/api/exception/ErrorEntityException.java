package com.nexters.wiw.api.exception;

import com.nexters.wiw.api.domain.error.ErrorEntity;
import com.nexters.wiw.api.domain.error.ErrorType;

public class ErrorEntityException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    protected ErrorType errorType;

    public ErrorEntityException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

    public ErrorEntity entity() {
        return new ErrorEntity(errorType, getMessage());
    }
}
