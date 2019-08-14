package com.nexters.wiw.api.ui;

import java.time.LocalDateTime;

import com.nexters.wiw.api.domain.Group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupRequestDto {
    private String name;
    private String description;
    private String profileImage;
    private String backgroundImage;
    private LocalDateTime created;
    private int memberLimit;
    private boolean active;

    public Group toEntity() {
        return Group.builder()
                    .name(this.name)
                    .description(this.description)
                    .profileImage(this.profileImage)
                    .backgroundImage(this.backgroundImage)
                    .created(this.created)
                    .memberLimit(this.memberLimit)
                    .active(this.active)
                    .build();
    }
}