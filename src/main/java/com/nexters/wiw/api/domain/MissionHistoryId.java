package com.nexters.wiw.api.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Column;

@Data
public class MissionHistoryId implements Serializable {
    private long mission;
    private long user;

    public MissionHistoryId() {}

    public MissionHistoryId(long mission, long user) {
        this.mission = mission;
        this.user = user;
    }
}