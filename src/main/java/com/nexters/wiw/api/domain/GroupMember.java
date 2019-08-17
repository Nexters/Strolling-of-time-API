package com.nexters.wiw.api.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "groupId", referencedColumnName = "group_id", insertable=false, updatable=false)
    @JsonBackReference
    private Group group;

    //groupMember : user (N:1)
    @Id
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "user_id", insertable=false, updatable=false)
    @JsonBackReference
    private User user;

    private boolean permission;
}
