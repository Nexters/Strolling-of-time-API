package com.nexters.wiw.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Table(name="`mission_consumption_history`")
@Getter
@Entity
@IdClass(MissionHistoryId.class)
public class MissionHistory extends TimeEntity {
    //missionHistory : mission (N:1)
    @Id
    @ManyToOne
    @JoinColumn(name = "mission_id")
    private Mission mission;

    //missionHistory : user (N:1)
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Column(nullable = false)
    private int time;

    @Builder
    public MissionHistory(int time) {
        this.time = time;
    }
}