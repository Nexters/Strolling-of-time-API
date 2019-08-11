package com.nexters.wiw.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Table(name = "`group_mission`")
@Getter
@Entity
public class Mission extends TimeEntity {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @OneToMany
    @JoinColumn(name = "mission_id")
    private Collection<MissionHistory> missionHistory;

    //group:mission (1:N)
    @Column(name = "group_id")
    private long groupId;

    @NotBlank
    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45)
    private String description;

    @NotBlank
    @Column(name = "expect_learning_time", nullable = false)
    private int expectLearningTime;

    @NotBlank
    @Column(nullable = false)
    private int estimate;

    @Builder
    public Mission(long groupId, String name, String description, int expectLearningTime, int estimate) {
        this.groupId = groupId;
        this.name = name;
        this.description = description;
        this.expectLearningTime = expectLearningTime;
        this.estimate = estimate;
    }

    public Mission update(Mission mission) {
        this.name = mission.name;
        this.description = mission.description;
        this.estimate = mission.estimate;
        this.expectLearningTime = mission.expectLearningTime;

        return this;
    }
}