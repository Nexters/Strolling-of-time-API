package com.nexters.wiw.api.ui;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.nexters.wiw.api.domain.GroupNotice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NoticeRequestDto {

    @NotNull
    private Long groupId;

    @NotNull
    @Size(min = 1, max = 100)
    private String title;

    @Size(min = 1, max = 255)
    private String content;

    private LocalDateTime created;

    public GroupNotice toEntity() {
        return GroupNotice.builder().title(this.title).content(this.content).created(this.created).build();
    }
}
