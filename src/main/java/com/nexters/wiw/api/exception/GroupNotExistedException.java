package com.nexters.wiw.api.exception;

public class GroupNotExistedException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private static final String MSG = "GROUP_NOT_EXISTED_EXCEPTION";

    public GroupNotExistedException() {
        super(MSG);
    }
}
