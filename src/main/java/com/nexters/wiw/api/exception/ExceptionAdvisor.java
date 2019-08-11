package com.nexters.wiw.api.exception;

import com.nexters.wiw.api.ui.ExceptionMessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class ExceptionAdvisor {

    @ExceptionHandler(MissionNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ExceptionMessageDto handleMissionNotFoundException(MissionNotFoundException e) {
        return new ExceptionMessageDto("MISSION_NOT_FOUND_EXCEPTION", e.getMessage());
    }

    @ExceptionHandler(MissionValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ExceptionMessageDto handleMissionValidationException(MissionValidationException e) {
        return new ExceptionMessageDto("MISSION_VALIDATION_EXCEPTION", e.getMessage());
    }
}