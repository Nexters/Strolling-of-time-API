package com.nexters.wiw.api.domain;

import java.io.Serializable;

import javax.persistence.Column;

public class MissionHistoryId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name="mission_id")
    private long missionId;

    //user:missionHistory (1:N)  
    @Column(name="user_id")
    private long userId;
}
