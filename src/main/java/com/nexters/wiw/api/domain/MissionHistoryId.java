package com.nexters.wiw.api.domain;

import java.io.Serializable;
import lombok.Data;

@Data
public class MissionHistoryId implements Serializable {
    private long mission;
    private long user;

    public MissionHistoryId() {
    }

    public MissionHistoryId(long mission, long user) {
        this.mission = mission;
        this.user = user;
    }
}
