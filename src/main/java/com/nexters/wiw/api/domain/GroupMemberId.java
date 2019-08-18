package com.nexters.wiw.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupMemberId implements Serializable {
    private long group;
    private long user;

    public GroupMemberId() {}

    public GroupMemberId(Long group, Long user) {
        this.group = group;
        this.user = user;
    }
}
