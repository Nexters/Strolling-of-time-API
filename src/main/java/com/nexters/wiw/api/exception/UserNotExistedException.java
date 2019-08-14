package com.nexters.wiw.api.exception;

public class UserNotExistedException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private static final String MSG = "USER_NOT_EXISTED_EXCEPTION";

    public UserNotExistedException() {
        super(MSG);
    }

}