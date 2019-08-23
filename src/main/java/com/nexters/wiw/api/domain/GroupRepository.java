package com.nexters.wiw.api.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GroupRepository extends JpaRepository<Group, Long>, JpaSpecificationExecutor<Group> {
    Optional<Group> findByName(String keyword);
    Optional<List<Group>> findByNameContaining(String keyword);
    Optional<List<Group>> findByCategoryContaining(String keyword);
}