package com.nexters.wiw.api.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    @Query(value = "SELECT `group`.* from `group`, group_member, users " +
            "WHERE `group`.group_id = group_member.group_id AND users.user_id = group_member.user_id AND users.user_id = :userid",
            nativeQuery = true)
    Page<Group> findAllByUserId(@Param("userid") Long id, Pageable pageable);
}