package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.domain.Mission;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MissionRequestDto {
    private long groupId;
    private String name;
    private String description;
    private int expectLearningTime;
    private int estimate;

    public Mission toEntity() {
        return Mission.builder()
                .groupId(this.groupId)
                .name(this.name)
                .description(this.description)
                .expectLearningTime(this.expectLearningTime)
                .estimate(this.estimate)
                .build();
    }
}
