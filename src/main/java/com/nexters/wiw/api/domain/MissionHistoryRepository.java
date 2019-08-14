package com.nexters.wiw.api.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionHistoryRepository extends JpaRepository<MissionHistory, Long> {
    //MissionHistory findByMissionAndUserId(long missionId, long userId);
}
