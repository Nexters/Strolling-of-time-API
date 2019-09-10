package com.nexters.wiw.api.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@DynamicInsert
@DynamicUpdate
@Builder
@EntityListeners(value = {AuditingEntityListener.class})
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`group_notice`")
public class GroupNotice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id", columnDefinition = "BIGINT(20) UNSIGNED")
    private long id;

    // groupNotice : group (N:1)
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "group_id")
    private Group group;

    // groupNotice : user (N:1)
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @NotBlank
    @Size(min = 1, max = 100)
    @Column(length = 100, nullable = false)
    private String title;

    @NotBlank
    @Size(min = 1, max = 255)
    @Column(length = 255, nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreatedDate
    private LocalDateTime created;

    public GroupNotice update(GroupNotice groupNotice) {
        this.title = groupNotice.getTitle();
        this.content = groupNotice.getContent();

        return this;
    }
}
