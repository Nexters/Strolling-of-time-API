package com.nexters.wiw.api.domain;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
@Builder
@DynamicInsert
@DynamicUpdate
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
    private List<GroupNotice> notices = new ArrayList<GroupNotice>();

    //group : mission (1:N)
    @OneToMany(mappedBy = "group", cascade = CascadeType.PERSIST)
    @JsonManagedReference
    private List<Mission> missions = new ArrayList<Mission>();

    //group : groupMember (1:N)
    @OneToMany(mappedBy = "group")
    @JsonManagedReference
    private List<GroupMember> member = new ArrayList<GroupMember>();

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
}
