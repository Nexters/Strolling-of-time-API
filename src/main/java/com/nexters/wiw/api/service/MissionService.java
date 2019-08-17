package com.nexters.wiw.api.service;

import java.util.List;

import com.nexters.wiw.api.domain.*;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.NotFoundException;
import com.nexters.wiw.api.exception.mission.MissionNotFoundException;
import com.nexters.wiw.api.ui.MissionRequestDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MissionService {

    private MissionRepository missionRepository;
    private GroupRepository groupRepository;

    @Transactional
    public Mission createMission(long groupId, MissionRequestDto dto) {
        Mission newMission = dto.toEntity();

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND, "ID에 해당하는 Group을 찾을 수 없습니다."));

        newMission.setGroup(group);

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

    public List<Mission> getGroupMission(long groupId) {
        //TODO 그룹 id로 그룹 존재하는지 체크

        List<Mission> mission = missionRepository.findByGroupId(groupId);
        if(mission.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND, "Group에 해당하는 Mission이 존재하지 않습니다.");

        return mission;
    }

	/* public List<Mission> getUserMission(long userId) {
        List<Mission> mission = missionRepository.findByUserId(userId);
        if(mission.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND, "User가 가지고 있는 Mission이 존재하지 않습니다.");

		return mission;
	} */

    @Transactional
    public void deleteMission(long id) {
        Mission mission = getMission(id);
        //
        missionRepository.delete(mission);
    }

    @Transactional
    public Mission updateMission(long id, MissionRequestDto dto) {
        Mission mission = getMission(id);

        return mission.update(dto.toEntity());
    }
}
