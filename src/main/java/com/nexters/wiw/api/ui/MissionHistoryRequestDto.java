package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.domain.MissionHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MissionHistoryRequestDto {
    private int time;

    public MissionHistory toEntity() {
        return MissionHistory.builder()
                .time(this.time)
                .build();
    }
}