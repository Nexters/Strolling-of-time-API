package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.common.ModelMapperUtil;
import com.nexters.wiw.api.domain.GroupMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupMemberResponseDto {
    private Long groupId;

    private Long userId;

    private boolean permission;

    static public GroupMemberResponseDto of(GroupMember groupMember) {
        GroupMemberResponseDto instance = new GroupMemberResponseDto();

        instance.groupId = groupMember.getGroup().getId();
        instance.userId = groupMember.getUser().getId();
        instance.permission = groupMember.isPermission();

        return instance;
    }
}
