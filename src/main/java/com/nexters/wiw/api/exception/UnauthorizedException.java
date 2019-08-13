package com.nexters.wiw.api.exception;

public class UnauthorizedException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    private static final String MSG = "UNAUTHORIZED_EXCEPTION";

    public UnauthorizedException() {
        super(MSG);
    }

}