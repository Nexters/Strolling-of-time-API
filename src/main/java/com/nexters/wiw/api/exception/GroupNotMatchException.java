package com.nexters.wiw.api.exception;

public class GroupNotMatchException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private static final String MSG = "GROUP_NOT_MATCH_EXCEPTION";

    public GroupNotMatchException() {
        super(MSG);
    }

}
