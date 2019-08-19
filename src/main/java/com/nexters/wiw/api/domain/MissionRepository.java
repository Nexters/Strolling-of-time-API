package com.nexters.wiw.api.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByGroupId(long groupId);

    List<Mission> findByGroupIdAndEstimateLessThanOrderByEstimate(long groupId, LocalDateTime now);
    List<Mission> findByGroupIdAndEstimateGreaterThanEqualOrderByEstimate(long groupId, LocalDateTime now);
}