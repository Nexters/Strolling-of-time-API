package com.nexters.wiw.api.domain;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@Table(name="`group_member`")
@Getter
@Setter
@Entity
@IdClass(GroupMemberId.class)
public class GroupMember {
    //groupMember : group (N:1)
    @Id
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    //groupMember : user (N:1)
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean permission;

    public GroupMember(User user){
        this.user = user;
    }
}
