package com.nexters.wiw.api.domain.error;

import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ErrorType {
    UNAUTHORIZED("unauthorized"), UNAUTHENTICATED("unauthenticated"), CONFLICT(
            "conflict"), NOT_FOUND("not found"), BAD_REQUEST("bad request");

    private String errorType;

    ErrorType(String errorType) {
        this.errorType = errorType;
    }

    @JsonValue
    public String getErrorType() {
        return errorType;
    }

    public static ErrorType of(String errorType) {
        return Arrays.stream(ErrorType.values()).filter(e -> e.getErrorType().equals(errorType))
                .findFirst().orElseThrow(RuntimeException::new);
    }
}
