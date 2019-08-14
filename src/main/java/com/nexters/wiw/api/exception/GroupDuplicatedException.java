package com.nexters.wiw.api.exception;

public class GroupDuplicatedException extends RuntimeException  {

    private static final long serialVersionUID = 1L;

    private static final String MSG = "GROUP_DUPLICATED_EXCEPTION";

    public GroupDuplicatedException() {
        super(MSG);
    }
}
