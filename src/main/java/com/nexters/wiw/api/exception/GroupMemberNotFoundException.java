package com.nexters.wiw.api.exception;

import com.nexters.wiw.api.domain.error.ErrorType;

public class GroupMemberNotFoundException extends NotFoundException  {

    public GroupMemberNotFoundException() {
        super(ErrorType.NOT_FOUND, "GROUP_MEMBER_DOES_NOT_EXIST");
    }
}
