package com.nexters.wiw.api.domain;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JacksonInject;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="`group_notice`")
public class GroupNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", columnDefinition = "BIGINT(20) UNSIGNED")
    private long id;

    //groupNotice : group (N:1)
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "groupId", referencedColumnName = "group_id",
            insertable = false, updatable = false)
    private Group group;

    //groupNotice : user (N:1)
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "userId", referencedColumnName = "user_id",
            insertable = false, updatable = false)
    private User user;

    @NotBlank
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String title;

    @NotBlank
    @Size(min = 1, max = 255)
    @Column(length = 255, nullable = false)
    private String content;

    private LocalDateTime created;

    @Builder
    public GroupNotice(String title, String content, LocalDateTime created) {
        this.title = title;
        this.content = content;
        this.created = created;
    }

    public GroupNotice update(String title, String content, LocalDateTime created) {
        this.title = title;
        this.content = content;
        this.created = created;
        
        return this;
    }
}
