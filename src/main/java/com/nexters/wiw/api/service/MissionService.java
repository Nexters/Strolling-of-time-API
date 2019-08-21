package com.nexters.wiw.api.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexters.wiw.api.domain.*;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.UnAuthorizedException;
import com.nexters.wiw.api.exception.MissionNotFoundException;
import com.nexters.wiw.api.ui.MissionRequestDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Mission getMission(long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new MissionNotFoundException(ErrorType.NOT_FOUND, "ID에 해당하는 Mission을 찾을 수 없습니다."));
    }

    public Page<Mission> getGroupMission(String authHeader, long groupId, Pageable pageable) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        //수행중인 미션, estimate 기준
        LocalDateTime now = LocalDateTime.now();
        Page<Mission> mission = missionRepository.findByGroupIdAndEstimateGreaterThan(groupId, now, pageable);

        if(mission.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND, "진행 중인 Group Mission이 존재하지 않습니다.");

        return mission;
    }

    public Page<Mission> getGroupEndMission(String authHeader, long groupId, Pageable pageable) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        //수행 완료 미션, estimate 기준
        LocalDateTime now = LocalDateTime.now();
        Page<Mission> mission = missionRepository.findByGroupIdAndEstimateLessThanEqual(groupId, now, pageable);

        if(mission.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND, "지난 Group Mission이 존재하지 않습니다.");

        return mission;
    }

    public List<Mission> getUserMission(String authHeader, long userId) {
        List<Group> groups = groupService.getGroupByUserId(authHeader, userId);

        List<Mission> result = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for(Group group : groups) {
            List<Mission> missions = missionRepository.findByGroupIdAndEstimateGreaterThanOrderByEstimate(group.getId(), now);
            result.addAll(missions);
        }
        if(result.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND, "진행 중인 User Mission이 존재하지 않습니다.");

        return result;
    }

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
