package com.nexters.wiw.api.service;

import java.time.LocalDateTime;
import java.util.List;

import com.nexters.wiw.api.domain.*;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.UnAuthorizedException;
import com.nexters.wiw.api.exception.MissionNotFoundException;
import com.nexters.wiw.api.ui.MissionRequestDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MissionService {

    private MissionRepository missionRepository;
    private GroupService groupService;
    private AuthService authService;

    @Transactional
    public Mission createMission(String authHeader, long groupId, MissionRequestDto dto) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        Mission newMission = dto.toEntity();
        Group group = groupService.getGroupById(authHeader, groupId);
        newMission.addGroup(group);

        return missionRepository.save(newMission);
    }

    public List<Mission> getMissionList() {
        List<Mission> mission = missionRepository.findAll();
        if(mission.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND, "Mission 목록이 존재하지 않습니다.");

        return mission;
    }

    public Mission getMission(long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new MissionNotFoundException(ErrorType.NOT_FOUND, "ID에 해당하는 Mission을 찾을 수 없습니다."));
    }

    public List<Mission> getGroupMission(String authHeader, long groupId) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        //수행중인 미션
        LocalDateTime now = LocalDateTime.now();
        List<Mission> mission = missionRepository.findByGroupIdAndEstimateGreaterThanEqual(groupId, now);
        if(mission.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND, "진행 중인 Group Mission이 존재하지 않습니다.");

        return mission;
    }

    public List<Mission> getGroupEndMission(String authHeader, long groupId) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        //수행 완료 미션
        LocalDateTime now = LocalDateTime.now();
        List<Mission> mission = missionRepository.findByGroupIdAndEstimateLessThan(groupId, now);
        if(mission.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND, "지난 Group Mission이 존재하지 않습니다.");

        return mission;
    }

	/* public List<Mission> getUserMission(long userId) {
        List<Mission> mission = missionRepository.findByUserId(userId);
        if(mission.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND, "User가 가지고 있는 Mission이 존재하지 않습니다.");

		return mission;
	} */

    @Transactional
    public void deleteMission(String authHeader, long id) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        Mission mission = getMission(id);
        missionRepository.delete(mission);
    }

    @Transactional
    public Mission updateMission(String authHeader, long id, MissionRequestDto dto) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        Mission mission = getMission(id);

        return mission.update(dto.toEntity());
    }
}
