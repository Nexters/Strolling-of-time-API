package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.domain.GroupNotice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GroupNoticeResponseDto {
    private Long id;
    private Long groupId;
    private Long userId;
    private String title;
    private String content;
    private LocalDateTime created;

    static public GroupNoticeResponseDto of(GroupNotice groupNotice) {
        GroupNoticeResponseDto instance = new GroupNoticeResponseDto();

        instance.id = groupNotice.getId();
        instance.groupId = groupNotice.getGroup().getId();
        instance.userId = groupNotice.getUser().getId();
        instance.title = groupNotice.getTitle();
        instance.content = groupNotice.getContent();
        instance.created = groupNotice.getCreated();

        return instance;
    }
}
