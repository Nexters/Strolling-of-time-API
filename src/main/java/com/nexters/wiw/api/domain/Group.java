package com.nexters.wiw.api.domain;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Getter
@Entity
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
    private List<GroupNotice> notices = new ArrayList<GroupNotice>();

    //group : mission (1:N)
    @OneToMany(mappedBy = "group")
    private List<Mission> missions = new ArrayList<Mission>();

    //group : groupMember (1:N)
    @OneToMany(mappedBy = "group")
    private List<GroupMember> member = new ArrayList<GroupMember>();

    @Column(length = 45, nullable = false)
    private String name;
    
    @Column(length = 100)
    private String description;

    @Column(name = "profile_image")
    @ColumnDefault(value = "'default_group_profile.png'")
    private String profileImage;

    @Column(name = "background_image")
    @ColumnDefault(value = "'default_group_background.png'")
    private String backgroundImage;

    private LocalDateTime created;

    @Column(name = "member_limit")
    @ColumnDefault(value = "6")
    private int memberLimit;

    @ColumnDefault(value = "true")
    private boolean active;

    @Builder
    public Group(String name, String description,
                 String profileImage, String backgroundImage,
                 LocalDateTime created, int memberLimit, boolean active){
        this.name = name;
        this.description = description;
        this.profileImage = profileImage;
        this.backgroundImage = backgroundImage;
        this.created = created;
        this.memberLimit = memberLimit;
        this.active = active;
    }

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