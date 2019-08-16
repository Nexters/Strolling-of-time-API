package com.nexters.wiw.api.exception;

public class UserDuplicatedException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private static final String MSG = "USER_DUPLICATED_EXCEPTION";

    public UserDuplicatedException() {
        super(MSG);
    }

}