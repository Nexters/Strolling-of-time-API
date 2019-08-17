package com.nexters.wiw.api.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.nexters.wiw.api.domain.Group;

import com.nexters.wiw.api.domain.GroupNotice;
import com.nexters.wiw.api.domain.Mission;
import lombok.*;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupRequestDto {

    @NotNull
    @Size(min = 1, max = 45)
    private String name;

    @Size(min = 1, max = 100)
    private String description;

    @Pattern(regexp = ".*\\.jpg|.*\\.JPG|.*\\.png|.*\\.PNG|.*\\.gif|.*\\.GIF", message = "jpg, png, gif 확장자의 이미지만 지원합니다.")
    private String profileImage;

    @Pattern(regexp = ".*\\.jpg|.*\\.JPG|.*\\.png|.*\\.PNG|.*\\.gif|.*\\.GIF", message = "jpg, png, gif 확장자의 이미지만 지원합니다.")
    private String backgroundImage;

    private LocalDateTime created;

    @Min(2) @Max(10)
    private int memberLimit = 6;

    private boolean active = true;

    public Group toEntity() {
        return Group.builder()
                    .name(this.name)
                    .description(this.description)
                    .profileImage(this.profileImage)
                    .backgroundImage(this.backgroundImage)
                    .created(this.created)
                    .memberLimit(this.memberLimit)
                    .active(this.active)
                    .missions(new ArrayList<Mission>())
                    .notices(new ArrayList<GroupNotice>())
                    .build();
    }
}
