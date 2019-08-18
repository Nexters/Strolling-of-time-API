package com.nexters.wiw.api.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionHistoryRepository extends JpaRepository<MissionHistory, MissionHistoryId> {
    MissionHistory findByMissionIdAndUserId(long missionId, long userId);
}
