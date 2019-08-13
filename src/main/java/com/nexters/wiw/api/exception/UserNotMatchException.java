package com.nexters.wiw.api.exception;

public class UserNotMatchException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private static final String MSG = "USER_NOT_MATCH_EXCEPTION";

    public UserNotMatchException() {
        super(MSG);
    }

}