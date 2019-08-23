package com.nexters.wiw.api.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    @Query(value = "SELECT g.* from `group` g, group_member gm, users u " +
            "WHERE g.group_id = gm.group_id AND u.user_id = gm.user_id AND u.user_id = :userid",
            nativeQuery = true)
    Page<Group> findAllByUserId(Pageable pageable, @Param("userid") Long id);
}