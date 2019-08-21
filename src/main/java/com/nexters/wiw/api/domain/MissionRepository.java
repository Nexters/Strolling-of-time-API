package com.nexters.wiw.api.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    Page<Mission> findByGroupIdAndEstimateLessThanEqual(long groupId, LocalDateTime now, Pageable pageable);
    Page<Mission> findByGroupIdAndEstimateGreaterThan(long groupId, LocalDateTime now, Pageable pageable);
    List<Mission> findByGroupIdAndEstimateGreaterThanOrderByEstimate(Long id, LocalDateTime now);
}