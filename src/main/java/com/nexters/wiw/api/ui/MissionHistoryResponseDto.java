package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.domain.MissionHistory;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MissionHistoryResponseDto {
    private int time;
    private LocalDateTime updated;

    public MissionHistoryResponseDto(MissionHistory missionHistory) {
        this.time = missionHistory.getTime();
        this.updated = missionHistory.getUpdated();
    }
}