package com.nexters.wiw.api.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nexters.wiw.api.ui.LoginReqeustDto;
import com.nexters.wiw.api.ui.UserPatchRequestDto;

import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
@EntityListeners(value = { AuditingEntityListener.class })
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    @Column(name = "user_id", columnDefinition = "BIGINT(20) UNSIGNED")
    private Long id;

    // user : missionHistory (1:N)
    @OneToMany(mappedBy = "user")
    private List<MissionHistory> missionHistories;

    // user: groupNotice (1:N)
    @OneToMany(mappedBy = "user")
    private List<GroupNotice> notices;

    // user : groupMember (1:N)
    @OneToMany(mappedBy = "user")
    private List<GroupMember> members;

    @NotBlank
    @Column(length = 50, nullable = false)
    private String nickname;

    @NotBlank
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(min = 8)
    @JsonIgnore
    @Column(length = 100, nullable = false)
    private String password;

    @ColumnDefault(value = "'default_user_profile.png'")
    @Column(name = "profile_image")
    private String profileImage;

    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    public User(String nickname, String email, String password, String profileImage) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
    }

    public User update(UserPatchRequestDto userPatchRequestDto) {
        this.nickname = userPatchRequestDto.getNickname();
        this.profileImage = userPatchRequestDto.getProfileImage();
        return this;
    }

    public boolean matchPassword(String password, PasswordEncoder bCryptPasswordEncoder) {
        return bCryptPasswordEncoder.matches(password, this.password);
    }
}