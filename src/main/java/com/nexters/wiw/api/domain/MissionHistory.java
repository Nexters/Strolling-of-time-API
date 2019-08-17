package com.nexters.wiw.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Table(name="`mission_consumption_history`")
@Getter
@Entity
@EntityListeners(value = { AuditingEntityListener.class })
@IdClass(MissionHistoryId.class)
public class MissionHistory extends TimeEntity {
    /* @Id
    private long missionId;

    @Id
    private long userId; */

    //missionHistory : mission (N:1)
    //연관관계만 맺는 역할만 하고 @Id 컬럼을 실제 값 매핑에 이용
    //mission_id -> missionId
    @Id
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "mission_id", referencedColumnName = "mission_id")
    private Mission mission;

    //missionHistory : user (N:1)
    //insertable = false, updatable = false
    @Id
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @NotNull
    @Column(nullable = false)
    private int time;

    @Builder
    public MissionHistory(int time) {
        /* this.missionId = missionId;
        this.userId = userId; */
        this.time = time;
    }

    public MissionHistory update(MissionHistory missionHistory) {
        this.time = missionHistory.time;

        return this;
    }
}