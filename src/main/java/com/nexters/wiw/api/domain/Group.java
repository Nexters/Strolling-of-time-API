package com.nexters.wiw.api.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
@EntityListeners(value = {AuditingEntityListener.class})
@NoArgsConstructor
@AllArgsConstructor
@Table(name="`group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", columnDefinition = "BIGINT(20) UNSIGNED")
    private Long id;

    //group : groupNotice (1:N)
    @OneToMany(mappedBy = "group")
    @JsonManagedReference
    private List<GroupNotice> notices;

    //group : mission (1:N)
    @OneToMany(mappedBy = "group")
    @JsonManagedReference
    private List<Mission> missions;

    //group : groupMember (1:N)
    @OneToMany(mappedBy = "group")
    @JsonManagedReference
    private List<GroupMember> members;

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
    @Min(2) @Max(10)
    @ColumnDefault(value = "6")
    @Builder.Default
    private int memberLimit = 6;

    @ColumnDefault(value = "true")
    @Builder.Default
    private boolean active= true;

    public Group update(Group group) {
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

    public void deleteGroupNotice(GroupNotice groupNotice) { this.notices.remove(groupNotice); }

    public void addGroupMember(GroupMember member) { this.members.add(member); }

    public void deleteGroupMember(GroupMember member) { this.members.remove(member); }

    public void addGroupMission(Mission mission) {
        this.missions.add(mission);
    }

    public void deleteGroupMission(Mission mission) { this.missions.remove(mission); }
}
