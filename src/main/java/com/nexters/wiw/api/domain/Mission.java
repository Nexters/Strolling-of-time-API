package com.nexters.wiw.api.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@NoArgsConstructor
@Table(name="`group_mission`")
@Getter
@Entity
public class Mission {
    @Id
    @GeneratedValue
    @Column
    private long id;

    @OneToMany
    @JoinColumn(name = "mission_id")
    private List<MissionHistory> MissionHistory;

    //group:mission (1:N)
    @Column
    private long group_id;

    @Column(length=45)
    private String name;

    @Column(length=45)
    private String description;

    @Column(name="expect_learning_time")
    private int expectLearningTime;

    @Column
    private int estimate;

    @Column
    private LocalDateTime created;
}