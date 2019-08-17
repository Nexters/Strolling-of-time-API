package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.domain.Mission;
import com.nexters.wiw.api.domain.MissionHistory;
import com.nexters.wiw.api.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MissionHistoryRequestDto {
    @NotNull
    private int time;

    //@NotNull
    private long userId;

    //@NotNull
    private long missionId;

    public MissionHistory toEntity() {
        return MissionHistory.builder()
                .time(this.time)
                .build();
    }
}