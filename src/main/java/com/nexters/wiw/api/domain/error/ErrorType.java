package com.nexters.wiw.api.domain.error;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ErrorType {
    UNAUTHORIZED("unauthorized"),
    UNAUTHENTICATED("unauthenticated"),
    USER("user"),
    CONFLICT("conflict");

    private String errorType;

    ErrorType(String errorType) {
        this.errorType = errorType;
    }

    @JsonValue
    public String getErrorType() {
        return errorType;
    }

    public static ErrorType of(String errorType) {
        return Arrays.stream(ErrorType.values())
                .filter(e -> e.getErrorType().equals(errorType))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
