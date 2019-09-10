package com.nexters.wiw.api.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "`group_member`")
@NoArgsConstructor
@Getter
@Entity
@IdClass(GroupMemberId.class)
public class GroupMember {
    // groupMember : group (N:1)
    @Id
    @ManyToOne
    @JoinColumn(name = "group_id", columnDefinition = "BIGINT(20) UNSIGNED", insertable = false,
            updatable = false, nullable = false)
    private Group group;

    // groupMember : user (N:1)
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT(20) UNSIGNED", insertable = false,
            updatable = false, nullable = false)
    private User user;

    private boolean permission;

    public GroupMember(Group group, User user, Boolean permission) {
        this.group = group;
        this.user = user;
        this.permission = permission;
    }
}
