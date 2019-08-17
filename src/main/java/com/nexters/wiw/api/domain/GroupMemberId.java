package com.nexters.wiw.api.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class GroupMemberId implements Serializable {
    private Long groupId;
    private Long userId;

    public GroupMemberId() {}
    public GroupMemberId(Long groupId, Long userId) {
        this.groupId = groupId;
        this.userId = userId;
    }
}
