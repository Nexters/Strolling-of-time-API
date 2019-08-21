package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.domain.Mission;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MissionResponseDto {
    private long id;
    private String title;
    private int expectLearningTime;
    private LocalDateTime start;
    private LocalDateTime estimate;

    public MissionResponseDto(Mission mission) {
        this.id = mission.getId();
        this.title = mission.getTitle();
        this.expectLearningTime = mission.getExpectLearningTime();
        this.start = mission.getStart();
        this.estimate = mission.getEstimate();
    }

    public static List<MissionResponseDto> ofList(List<Mission> missions) {
        List<MissionResponseDto> response = missions.stream().map(MissionResponseDto::new).collect(Collectors.toList());

        return response;
    }
}
