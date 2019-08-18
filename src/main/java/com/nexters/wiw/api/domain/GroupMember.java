package com.nexters.wiw.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import javax.persistence.*;

@Table(name="`group_member`")
@NoArgsConstructor
@Getter
@Entity
@IdClass(GroupMemberId.class)
@Embeddable
public class GroupMember {
    //groupMember : group (N:1)
    @Id
    @ManyToOne
    @JoinColumn(name = "group_id", columnDefinition = "BIGINT(20) UNSIGNED",
            insertable=false, updatable=false)
    @JsonBackReference
    private Group group;

    //groupMember : user (N:1)
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT(20) UNSIGNED",
            insertable=false, updatable=false)
    @JsonBackReference
    private User user;

    private boolean permission;

    public GroupMember(Group group, User user, Boolean permission) {
        this.group = group;
        this.user = user;
        this.permission = permission;
    }
}
