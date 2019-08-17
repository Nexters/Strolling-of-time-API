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
public class GroupMember {

    //groupMember : group (N:1)
    @Id
    @ManyToOne
    @JoinColumn(name = "group_id", insertable=false, updatable=false)
    @JsonManagedReference("group_id")
    @JsonBackReference
    private Group group;

    //groupMember : user (N:1)
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", insertable=false, updatable=false)
    @JsonManagedReference("user_id")
    @JsonBackReference
    private User user;

    private boolean permission;

    public GroupMember(Group group, User user, Boolean permission) {
        this.group = group;
        this.user = user;
        this.permission = permission;
    }
}
