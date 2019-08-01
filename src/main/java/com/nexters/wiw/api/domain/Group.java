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

import org.hibernate.annotations.ColumnDefault;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(staticName = "of", access = AccessLevel.PROTECTED)
@Table(name="`group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT(20) UNSIGNED")
    private Long id;

    @NotBlank
    @Size(min = 1, max = 45)
    @Column(length = 45, nullable = false)
    private String name;
    
    @Size(min = 1, max = 255)
    @Column(length = 255, nullable = true)
    private String description;

    @ColumnDefault(value = "'default_uer_profile.png'")
    @Column(name = "profile_image", nullable = false)
    private String profileImage;

    @ColumnDefault(value = "'default_uer_profile.png'")
    @Column(name = "background_image", nullable = false)
    private String backgroundImage;

    private LocalDateTime created;

    @ColumnDefault(value = "6")
    @Column(name = "member_limit", nullable = false)
    private int memberLimit;

    @ColumnDefault(value = "true")
    @Column(nullable = false)
    private boolean active;
    //FIXME: ERD에는 BIT형인데 boolean으로 처리해도 될런지요?

    @Builder
    public Group(String name){
        this.name = name;
    }
}