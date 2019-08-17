package com.nexters.wiw.api.exception;

import java.util.Arrays;
import java.util.List;

import com.nexters.wiw.api.domain.error.ErrorEntity;
import com.nexters.wiw.api.domain.error.ErrorType;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionAdvisor {

    @ExceptionHandler(UnAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public List<ErrorEntity> handleUnAuthenticationException(ErrorEntityException exception) {
        return Arrays.asList(exception.entity());
    }

    @ExceptionHandler(UnAuthorizedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public List<ErrorEntity> handleUnAuthorizedException(ErrorEntityException exception) {
        return Arrays.asList(exception.entity());
    }

    @ExceptionHandler(value = {NotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public List<ErrorEntity> handleNotFoundException(ErrorEntityException exception) {
        return Arrays.asList(exception.entity());
    }

    @ExceptionHandler(value = {DataIntegrityViolationException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorEntity handleDuplicationException(DataIntegrityViolationException exception){
        return ErrorEntity.builder()
            .message("DB_CONFLICT")
            .errorType(ErrorType.CONFLICT)
            .build();
    }

    @ExceptionHandler(value = {EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorEntity handleNotFoundIdException(EmptyResultDataAccessException exception) {
        return ErrorEntity.builder()
            .message("NOT_FOUND_ID")
            .errorType(ErrorType.NOT_FOUND)
            .build();
    }



}
