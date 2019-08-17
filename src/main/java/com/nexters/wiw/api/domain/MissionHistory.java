package com.nexters.wiw.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Table(name="`mission_consumption_history`")
@Getter
@Entity
@EntityListeners(value = { AuditingEntityListener.class })
@IdClass(MissionHistoryId.class)
public class MissionHistory extends TimeEntity {
    //missionHistory : mission (N:1)
    @Id
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "mission_id", columnDefinition = "BIGINT(20) UNSIGNED", referencedColumnName = "mission_id",
            insertable = false, updatable = false)
    private Mission mission;

    //missionHistory : user (N:1)
    @Id
    @JsonBackReference
    @JsonManagedReference("user_id")
    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT(20) UNSIGNED", referencedColumnName = "user_id",
            insertable = false, updatable = false)
    private User user;

    @NotNull
    @Column(nullable = false)
    private int time;

    public void addMission(Mission mission) {
        this.mission = mission;
    }

    public void addUser(User user) {
        this.user = user;
    }

    @Builder
    public MissionHistory(User user, Mission mission, int time) {
        this.mission = mission;
        this.user = user;
        this.time = time;
    }

    public MissionHistory update(MissionHistory missionHistory) {
        this.time = missionHistory.time;

        return this;
    }
}