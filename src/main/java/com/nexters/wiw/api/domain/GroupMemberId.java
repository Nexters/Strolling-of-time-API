package com.nexters.wiw.api.domain;

import java.io.Serializable;
import java.util.Objects;
import lombok.Data;

@Data
public class GroupMemberId implements Serializable {
    private Long group;
    private Long user;

    public GroupMemberId() {
    }

    public GroupMemberId(Long group, Long user) {
        this.group = group;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GroupMemberId that = (GroupMemberId) o;
        return Objects.equals(group, that.group) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(group, user);
    }

}
