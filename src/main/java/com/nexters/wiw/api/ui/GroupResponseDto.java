package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.domain.GroupMember;
import com.nexters.wiw.api.domain.GroupNotice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupResponseDto {
    private Long id;
    private List<GroupMemberResponseDto> members;
    private List<GroupNoticeResponseDto> notices;
//    private List<MissionResponseDto> missions;
    private String name;
    private String description;
    private String profileImage;
    private String backgroundImage;
    private LocalDateTime created;
    private int memberLimit;
    private boolean active;

    static public GroupResponseDto of(Group group) {
        GroupResponseDto instance = new GroupResponseDto();

        instance.id = group.getId();
        instance.notices = new ArrayList<>();
        instance.members = new ArrayList<>();
        instance.name = group.getName();
        instance.description = group.getDescription();
        instance.profileImage = group.getProfileImage();
        instance.backgroundImage = group.getBackgroundImage();
        instance.created = group.getCreated();
        instance.memberLimit = group.getMemberLimit();
        instance.active = group.isActive();

        for(GroupMember groupMember : group.getMembers()){
            instance.members.add(GroupMemberResponseDto.of(groupMember));
        }

        for(GroupNotice groupNotice : group.getNotices()){
            instance.notices.add(GroupNoticeResponseDto.of(groupNotice));
        }

        return instance;
    }
}
