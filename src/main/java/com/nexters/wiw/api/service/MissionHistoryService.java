package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.*;
import com.nexters.wiw.api.ui.MissionHistoryRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MissionHistoryService {

    private MissionHistoryRepository missionHistoryRepository;
    private MissionService missionService;
    private AuthService authService;
    private UserService userService;

    public MissionHistory getMissionTime(long missionId, long userId) {
        return missionHistoryRepository.findByMissionIdAndUserId(missionId, userId);
    }

    @Transactional
    public void createMissionTime(long missionId, String authHeader, MissionHistoryRequestDto dto) {
        long userId = authService.findIdByToken(authHeader);
        User user = userService.getOne(userId);
        Mission mission = missionService.getMission(missionId);

        MissionHistory newMissionHistory = dto.toEntity();
        newMissionHistory.addMission(mission);
        newMissionHistory.addUser(user);

        missionHistoryRepository.save(newMissionHistory);
    }
}
