package com.nexters.wiw.api.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Table(name="`mission_consumption_history`")
@Getter
@Entity
@IdClass(MissionHistoryId.class)
@SQLInsert(sql = "INSERT INTO mission_consumption_history(missionId, userId, time)" +
        "VALUES (?, ?, ?) ON DUPLICATE KEY UPDATE time = VALUES(time)")
public class MissionHistory extends TimeEntity {
    //mission_id:missionHistory (1:N)
    @Id
    private long missionId;

    //user:missionHistory (1:N)
    @Id
    private long userId;

    @NotNull
    @Column(nullable = false)
    private int time;

    @Builder
    public MissionHistory(int time) {
        this.time = time;
    }
}