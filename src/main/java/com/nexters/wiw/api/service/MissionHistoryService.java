package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.Mission;
import com.nexters.wiw.api.domain.MissionHistory;
import com.nexters.wiw.api.domain.MissionHistoryRepository;
import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.ui.MissionHistoryRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionHistoryService {
    final MissionHistoryRepository missionHistoryRepository;
    final MissionService missionService;
    final UserService userService;

    public MissionHistory getMissionTime(long missionId, long userId) {
        return missionHistoryRepository.findByMissionIdAndUserId(missionId, userId);
    }

    @Transactional
    public void createMissionTime(long missionId, Long userId, MissionHistoryRequestDto dto) {
        User user = userService.getOne(userId);
        Mission mission = missionService.getMission(missionId);

        MissionHistory newMissionHistory = dto.toEntity();
        newMissionHistory.addMission(mission);
        newMissionHistory.addUser(user);

        missionHistoryRepository.save(newMissionHistory);
    }
}
