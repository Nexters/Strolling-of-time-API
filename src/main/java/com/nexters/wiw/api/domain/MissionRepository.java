package com.nexters.wiw.api.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByGroupId(long groupId);
}
