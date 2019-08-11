package com.nexters.wiw.api.exception;

public class MissionNotFoundException extends RuntimeException {

    public MissionNotFoundException(String message) {
        super(message);
    }
}