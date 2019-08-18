package com.nexters.wiw.api.ui;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.domain.Mission;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MissionRequestDto {
    @NotNull
    private String name;

    @Size(max = 45)
    private String description;

    @NotNull
    private int expectLearningTime;

    @NotNull
    private LocalDateTime estimate;

    public Mission toEntity() {
        return Mission.builder()
                .name(this.name)
                .description(this.description)
                .expectLearningTime(this.expectLearningTime)
                .estimate(this.estimate)
                .build();
    }
}
