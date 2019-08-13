package com.nexters.wiw.api.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

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
    @Column(columnDefinition = "BIGINT(20) UNSIGNED")
    private long id;

    @Column(name="group_id", columnDefinition = "BIGINT(20) UNSIGNED")
    private long groupId;

    @Column(name="user_id", columnDefinition = "BIGINT(20) UNSIGNED")
    private long userId;

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
    public GroupNotice(String title, String content, LocalDateTime created, int userId) {
        this.title = title;
        this.content = content;
        this.created = created;
        this.userId = userId;
    }

    public GroupNotice update(String title, String content, LocalDateTime created) {
        this.title = title;
        this.content = content;
        this.created = created;
        
        return this;
    }
}
