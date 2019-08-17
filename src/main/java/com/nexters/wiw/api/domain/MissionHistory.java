package com.nexters.wiw.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Id
    private long missionId;

    @Id
    private long userId;

    //missionHistory : mission (N:1)
    //연관관계만 맺는 역할만 하고 @Id 컬럼을 실제 값 매핑에 이용
    //mission_id -> missionId
    //@Id
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "missionId", referencedColumnName = "mission_id",
            insertable = false, updatable = false)
    private Mission mission;

    //missionHistory : user (N:1)
    //@Id
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "user_id",
            insertable = false, updatable = false)
    private User user;

    @NotNull
    @Column(nullable = false)
    private int time;

    @Builder
    public MissionHistory(long missionId, long userId, int time) {
        this.missionId = missionId;
        this.userId = userId;
        this.time = time;
    }

    public MissionHistory update(MissionHistory missionHistory) {
        this.time = missionHistory.time;

        return this;
    }
}