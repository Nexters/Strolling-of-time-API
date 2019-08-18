package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.domain.GroupNotice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupNoticeRequestDto {


    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @Size(min = 1, max = 255)
    private String content;

    private LocalDateTime created;

    public GroupNotice toEntity() {
        return GroupNotice.builder()
                          .title(this.title)
                          .content(this.content)
                          .created(this.created)
                          .build();
    }
}
