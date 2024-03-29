package com.nexters.wiw.api.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Table(name = "`group_mission`", schema = "imdeo")
@Getter
@Entity
@EntityListeners(value = {AuditingEntityListener.class})
public class Mission {
    @Id
    @GeneratedValue
    @Column(name = "mission_id", columnDefinition = "BIGINT(20) UNSIGNED")
    private long id;

    // mission : missionHistory (1:N)
    @OneToMany(mappedBy = "mission")
    // @JsonIgnore
    private List<MissionHistory> missionHistories = new ArrayList<MissionHistory>();

    // mission : group (N:1)
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

    /*
     * @ColumnDefault(value = "''") private String status;
     */

    // timezone = "Asia/Seoul"
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
    public Mission(String title, int expectLearningTime, LocalDateTime start,
            LocalDateTime estimate) {
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
