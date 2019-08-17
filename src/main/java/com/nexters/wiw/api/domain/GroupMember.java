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
    @Id
    private long groupId;

    @Id
    private  long userId;

    //groupMember : group (N:1)
    //@Id
    @ManyToOne
    @JsonBackReference(value = "group")
    @JoinColumn(name = "groupId", referencedColumnName = "group_id")
    private Group group;

    //groupMember : user (N:1)
    //@Id
    @ManyToOne
    @JsonBackReference(value = "user")
    @JoinColumn(name = "userId", referencedColumnName = "user_id")
    private User user;

    private boolean permission;
}
