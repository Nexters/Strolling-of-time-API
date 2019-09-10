package com.nexters.wiw.api.domain;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface GroupMemberRepository extends CrudRepository<GroupMember, GroupMemberId> {
    Optional<GroupMember> findByGroupAndUser(Group group, User user);
}
