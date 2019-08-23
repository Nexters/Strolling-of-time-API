package com.nexters.wiw.api.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface GroupMemberRepository extends CrudRepository<GroupMember, GroupMemberId> {
    Optional<GroupMember> findByGroupAndUser(Group group, User user);
}
