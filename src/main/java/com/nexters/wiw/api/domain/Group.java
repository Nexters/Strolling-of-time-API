package com.nexters.wiw.api.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EntityListeners(value = { AuditingEntityListener.class })
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "`group`", schema = "imdeo")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", columnDefinition = "BIGINT(20) UNSIGNED")
    private Long id;

    // group : groupNotice (1:N)
    @OneToMany(mappedBy = "group")
    private List<GroupNotice> notices;

    // group : mission (1:N)
    @OneToMany(mappedBy = "group")
    private List<Mission> missions;

    // group : groupMember (1:N)
    @OneToMany(mappedBy = "group")
    private List<GroupMember> members;

    @Column(nullable = false)
    private String category;

    @Column(length = 45, nullable = false, unique = true)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(name = "profile_image")
    @ColumnDefault(value = "'default_group_profile.png'")
    @Pattern(regexp = ".*\\.jpg|.*\\.JPG|.*\\.png|.*\\.PNG|.*\\.gif|.*\\.GIF", message = "jpg, png, gif 확장자의 이미지만 지원합니다.")
    private String profileImage;

    @Column(name = "background_image")
    @ColumnDefault(value = "'default_group_background.png'")
    @Pattern(regexp = ".*\\.jpg|.*\\.JPG|.*\\.png|.*\\.PNG|.*\\.gif|.*\\.GIF", message = "jpg, png, gif 확장자의 이미지만 지원합니다.")
    private String backgroundImage;

    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreatedDate
    private LocalDateTime created;

    @Column(name = "member_limit")
    @Min(2)
    @Max(10)
    @ColumnDefault(value = "6")
    private int memberLimit;

    @ColumnDefault(value = "true")
    private boolean active;

    public Group update(Group group) {
        this.category = group.category;
        this.name = group.name;
        this.description = group.description;
        this.profileImage = group.profileImage;
        this.backgroundImage = group.backgroundImage;
        this.created = group.created;
        this.memberLimit = group.memberLimit;
        this.active = group.active;

        return this;
    }

    public void addGroupNotice(GroupNotice groupNotice) {
        this.notices.add(groupNotice);
    }

    public void deleteGroupNotice(GroupNotice groupNotice) {
        this.notices.remove(groupNotice);
    }

    public void addGroupMember(GroupMember member) {
        this.members.add(member);
    }

    public void deleteGroupMember(GroupMember member) {
        this.members.remove(member);
    }

    public void addGroupMission(Mission mission) {
        this.missions.add(mission);
    }

    public void deleteGroupMission(Mission mission) {
        this.missions.remove(mission);
    }
}
