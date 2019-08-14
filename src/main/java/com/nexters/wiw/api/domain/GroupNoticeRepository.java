package com.nexters.wiw.api.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupNoticeRepository extends JpaRepository<Group, Long> {
    List<Group> findByNameContains(String keyword);
}