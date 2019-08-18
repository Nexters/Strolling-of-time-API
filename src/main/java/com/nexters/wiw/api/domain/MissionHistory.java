package com.nexters.wiw.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@Table(name="`mission_consumption_history`")
@Getter
@Entity
@EntityListeners(value = { AuditingEntityListener.class })
@IdClass(MissionHistoryId.class)
public class MissionHistory {
    //missionHistory : mission (N:1)
    @Id
    @JsonBackReference(value = "mission")
    @ManyToOne
    @JoinColumn(name = "mission_id", columnDefinition = "BIGINT(20) UNSIGNED",
            referencedColumnName = "mission_id", insertable = false, updatable = false)
    private Mission mission;

    //missionHistory : user (N:1)
    @Id
    @JsonBackReference(value = "user")
    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT(20) UNSIGNED",
            referencedColumnName = "user_id", insertable = false, updatable = false)
    private User user;

    @NotNull
    @Column(nullable = false)
    private int time;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @LastModifiedDate
    private LocalDateTime updated;


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