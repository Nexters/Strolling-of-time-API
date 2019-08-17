package com.nexters.wiw.api.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@DynamicInsert
@DynamicUpdate
@NoArgsConstructor
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

    @NotBlank
    @Size(min = 1, max = 45)
    @Column(length = 45, nullable = false)
    private String name;
    
    @Size(min = 1, max = 255)
    @Column(length = 255, nullable = true)
    private String description;

    @ColumnDefault(value = "'default_group_profile.png'")
    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @ColumnDefault(value = "'default_group_background.png'")
    @Column(name = "background_image", nullable = false)
    private String backgroundImage;

    private LocalDateTime created;

    @ColumnDefault(value = "6")
    @Column(name = "member_limit", nullable = false)
    private int memberLimit;

    @ColumnDefault(value = "true")
    @Column(nullable = false)
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