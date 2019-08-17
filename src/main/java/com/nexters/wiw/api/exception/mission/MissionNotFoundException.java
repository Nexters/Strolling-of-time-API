package com.nexters.wiw.api.exception.mission;

import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.NotFoundException;

public class MissionNotFoundException extends NotFoundException {

    public MissionNotFoundException(ErrorType errorType, String message) {
        super(errorType, message);
    }
}