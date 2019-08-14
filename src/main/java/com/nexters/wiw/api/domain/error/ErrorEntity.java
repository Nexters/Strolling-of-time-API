package com.nexters.wiw.api.domain.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorEntity {
    private ErrorType errorType;
    private String message;

    public void setErrorType(String errorType) {
        this.errorType = ErrorType.of(errorType);
    }

    public boolean sameErrorType(ErrorType type) {
        return errorType == type;
    }
}
