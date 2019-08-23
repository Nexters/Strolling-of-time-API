package com.nexters.wiw.api.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    Optional<Group> findByName(String keyword);
    Optional<List<Group>> findByNameContaining(String keyword);
    Optional<List<Group>> findByCategoryContaining(String keyword);

    @Query(value = "SELECT g.* from imdeo.group g, imdeo.group_member gm, imdeo.users u " +
            "WHERE g.group_id = gm.group_id AND u.user_id = gm.user_id AND u.user_id = :userid",
            nativeQuery = true)
    Optional<List<Group>> findAllByUserId(@Param("userid") Long id);
}