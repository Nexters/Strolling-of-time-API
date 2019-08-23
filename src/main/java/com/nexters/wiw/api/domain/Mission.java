package com.nexters.wiw.api.domain;

import com.fasterxml.jackson.annotation.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Table(name = "`group_mission`")
@Getter
@Entity
@EntityListeners(value = { AuditingEntityListener.class })
public class Mission {
    @Id
    @GeneratedValue
    @Column(name = "mission_id", columnDefinition = "BIGINT(20) UNSIGNED")
    private long id;

    //mission : missionHistory (1:N)
    @OneToMany(mappedBy = "mission")
    private List<MissionHistory> missionHistories = new ArrayList<MissionHistory>();

    //mission : group (N:1)
    @ManyToOne
    @JsonBackReference(value = "group")
    @JoinColumn(name = "group_id", referencedColumnName = "group_id")
    private Group group;

    @NotNull
    @Column(length = 45, nullable = false)
    private String title;

    @NotNull
    @Column(name = "expect_learning_time", nullable = false)
    private int expectLearningTime;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime start;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(nullable = false)
    private LocalDateTime estimate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreatedDate
    private LocalDateTime created;

    public void addGroup(Group group) {
        this.group = group;
    }

    @Builder
    public Mission(String title, int expectLearningTime, LocalDateTime start, LocalDateTime estimate) {
        this.title = title;
        this.expectLearningTime = expectLearningTime;
        this.start = start;
        this.estimate = estimate;
    }

    public Mission update(Mission mission) {
        this.title = mission.title;
        this.start = mission.start;
        this.estimate = mission.estimate;
        this.expectLearningTime = mission.expectLearningTime;

        return this;
    }
}