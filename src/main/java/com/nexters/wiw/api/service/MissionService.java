package com.nexters.wiw.api.service;

import java.time.LocalDateTime;
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

    /* public List<Mission> getMissionList() {
        List<Mission> mission = missionRepository.findAll();
        if(mission.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND, "Mission 목록이 존재하지 않습니다.");

        return mission;
    } */

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

	public Map<String, List<Mission>> getUserMission(String authHeader, long[] groupPk) {
        Map<String, List<Mission>> map = new HashMap<>();

        LocalDateTime now = LocalDateTime.now();
        for(long pk : groupPk){
            List<Mission> mission = missionRepository.findByGroupIdAndEstimateGreaterThanOrderByEstimate(pk, now);
            if(!mission.isEmpty())
                map.put("그룹 번호 "+pk, mission);
        }

        if(map.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND, "진행 중인 User Mission이 존재하지 않습니다.");

		return map;
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
