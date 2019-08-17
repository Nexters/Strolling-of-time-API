package com.nexters.wiw.api.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByName(String keyword);
    Optional<List<Group>> findByNameContaining(String keyword);
}