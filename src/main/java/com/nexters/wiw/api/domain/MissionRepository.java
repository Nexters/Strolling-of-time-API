package com.nexters.wiw.api.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.nexters.wiw.api.ui.MissionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MissionRepository extends JpaRepository<Mission, Long> {
    Page<Mission> findByGroupIdAndEstimateLessThanEqual(long groupId, LocalDateTime now, Pageable pageable);
    Page<Mission> findByGroupIdAndEstimateGreaterThan(long groupId, LocalDateTime now, Pageable pageable);
    List<Mission> findByGroupIdAndEstimateGreaterThanOrderByEstimate(Long id, LocalDateTime now);

    @Query(value = "SELECT m.* FROM group_mission m " +
                   "WHERE m.group_id IN " +
                   "(SELECT g.group_id FROM `group` g, group_member gm, users u " +
                   "WHERE g.group_id = gm.group_id AND u.user_id = gm.user_id AND u.user_id = :userid)",
           nativeQuery = true)
    Page<Mission> findAllByUserId(Pageable pageable, @Param("userid") long userId);
}