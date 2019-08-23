package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.*;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.UnAuthorizedException;
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

    public MissionHistory getMissionTime(long missionId, String authHeader) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");
        long userId = authService.findIdByToken(authHeader);

        return missionHistoryRepository.findByMissionIdAndUserId(missionId, userId);
    }

    @Transactional
    public void createMissionTime(long missionId, String authHeader, MissionHistoryRequestDto dto) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        long userId = authService.findIdByToken(authHeader);
        User user = userService.getOne(authHeader, userId);
        Mission mission = missionService.getMission(missionId);

        MissionHistory newMissionHistory = dto.toEntity();
        newMissionHistory.addMission(mission);
        newMissionHistory.addUser(user);

        missionHistoryRepository.save(newMissionHistory);
    }
}
