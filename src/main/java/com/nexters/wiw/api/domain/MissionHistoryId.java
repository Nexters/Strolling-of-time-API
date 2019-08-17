package com.nexters.wiw.api.domain;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Column;

@Data
public class MissionHistoryId implements Serializable {
    //private Mission mission;
    //private User user;
    private long missionId;
    private long userId;
}