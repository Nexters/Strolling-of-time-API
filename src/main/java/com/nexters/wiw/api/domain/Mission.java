package com.nexters.wiw.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Table(name = "`group_mission`")
@Getter
@Entity
public class Mission extends TimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "mission_id", columnDefinition = "BIGINT(20) UNSIGNED")
    private long id;

    //mission : missionHistory (1:N)
    @OneToMany(mappedBy = "mission")
    private List<MissionHistory> missionHistories = new ArrayList<MissionHistory>();

    //mission : group (N:1)
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @NotNull
    @Column(length = 45, nullable = false)
    private String name;

    @Column(length = 45)
    private String description;

    @NotNull
    @Column(name = "expect_learning_time", nullable = false)
    private int expectLearningTime;

    @NotNull
    @Column(nullable = false)
    private int estimate;

    @Builder
    public Mission(String name, String description, int expectLearningTime, int estimate) {
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