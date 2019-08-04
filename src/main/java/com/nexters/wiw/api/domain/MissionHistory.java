package com.nexters.wiw.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@NoArgsConstructor
@Table(name="`mission_consumption_history`")
@Getter
@Entity
@IdClass(MissionHistoryId.class)
public class MissionHistory {
    // mission_id:missionHistory (1:N)
    @Id
    private long missionId;

    //user:missionHistory (1:N)
    @Id
    private long userId;

    @Column
    private int time;

    @Column
    private LocalDateTime updated;
}