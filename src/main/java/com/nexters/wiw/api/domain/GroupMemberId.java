package com.nexters.wiw.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupMemberId implements Serializable {
    private Group group;
    private User user;
}
