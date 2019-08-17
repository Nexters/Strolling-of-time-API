package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.*;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.UserNotExistedException;
import com.nexters.wiw.api.exception.mission.MissionNotFoundException;
import com.nexters.wiw.api.ui.MissionHistoryRequestDto;
import com.nexters.wiw.api.ui.MissionRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class MissionHistoryService {

    private MissionHistoryRepository missionHistoryRepository;
    private UserRepository userRepository;
    private MissionRepository missionRepository;

    private AuthService authService;

    /* @Transactional
    public void updateMissionTime(long missionId, long userId, MissionHistoryRequestDto dto) {
        MissionHistory time = getMissionTime(missionId, userId);
        time.update(dto.toEntity());
    } */

    public MissionHistory getMissionTime(long missionId, long userId) {
        return missionHistoryRepository.findByMissionIdAndUserId(missionId, userId);
    }

    @Transactional
    public void createMissionTime(long missionId, String authHeader, MissionHistoryRequestDto dto) {
        long userId = authService.findIdByToken(authHeader);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotExistedException(ErrorType.NOT_FOUND, "ID에 해당하는 유저를 찾을 수 없습니다."));

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new MissionNotFoundException(ErrorType.NOT_FOUND, "ID에 해당하는 미션을 찾을 수 없습니다."));

        MissionHistory newMissionHistory = dto.toEntity();
        newMissionHistory.addMission(mission);
        newMissionHistory.addUser(user);

        missionHistoryRepository.save(newMissionHistory);
    }
}
