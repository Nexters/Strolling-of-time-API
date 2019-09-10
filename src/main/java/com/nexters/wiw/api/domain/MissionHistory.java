package com.nexters.wiw.api.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name = "`mission_consumption_history`")
@Getter
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
@IdClass(MissionHistoryId.class)
public class MissionHistory {
    // missionHistory : mission (N:1)
    @Id
    @ManyToOne
    @JoinColumn(name = "mission_id", columnDefinition = "BIGINT(20) UNSIGNED",
            referencedColumnName = "mission_id", insertable = false, updatable = false)
    private Mission mission;

    // missionHistory : user (N:1)
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
