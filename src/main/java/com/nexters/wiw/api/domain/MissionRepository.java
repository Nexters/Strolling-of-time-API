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

    @Query(value = "SELECT group_mission.* " +
                   "FROM group_mission, group_member " +
                   "WHERE group_mission.group_id = group_member.group_id AND group_member.user_id = :userid ",
           nativeQuery = true)
    Page<Mission> findAllByUserId(@Param("userid") long userId, Pageable pageable);
}