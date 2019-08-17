package com.nexters.wiw.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    //@Column(name = "mission_id", insertable = false, updatable = false)
    //private long missionId;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "mission_id", referencedColumnName = "mission_id")
    private Mission mission;

    @Id
    //missionHistory : user (N:1)
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @NotNull
    @Column(nullable = false)
    private int time;

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public void setUser(User user) {
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