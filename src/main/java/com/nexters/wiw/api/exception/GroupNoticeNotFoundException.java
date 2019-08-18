package com.nexters.wiw.api.exception;

import com.nexters.wiw.api.domain.error.ErrorType;

public class GroupNoticeNotFoundException extends NotFoundException {

    public GroupNoticeNotFoundException() {
        super(ErrorType.NOT_FOUND, "GROUP_NOTICE_DOES_NOT_EXIST");
    }

}
