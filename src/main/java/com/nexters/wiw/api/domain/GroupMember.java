package com.nexters.wiw.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Table(name="`group_member`")
@Getter
@Entity
@IdClass(GroupMemberId.class)
public class GroupMember {
    //groupMember : group (N:1)
    @Id
    @ManyToOne
    @JsonBackReference(value = "group")
    @JoinColumn(name = "group_id", referencedColumnName = "group_id",
            columnDefinition = "BIGINT(20) UNSIGNED", insertable = false, updatable = false)
    private Group group;

    //groupMember : user (N:1)
    @Id
    @ManyToOne
    @JsonBackReference(value = "user")
    @JoinColumn(name = "user_id", referencedColumnName = "user_id",
            columnDefinition = "BIGINT(20) UNSIGNED", insertable = false, updatable = false)
    private User user;

    private boolean permission;

    public GroupMember(Group group, User user, Boolean permission) {
        this.group = group;
        this.user = user;
        this.permission = permission;
    }
}
