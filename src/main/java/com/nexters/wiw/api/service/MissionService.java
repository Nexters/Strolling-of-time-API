package com.nexters.wiw.api.service;

import java.util.List;

import com.nexters.wiw.api.domain.Mission;
import com.nexters.wiw.api.domain.MissionHistoryRepository;
import com.nexters.wiw.api.domain.MissionRepository;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.MissionNotFoundException;
import com.nexters.wiw.api.ui.MissionRequestDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MissionService {

	private MissionRepository missionRepository;
	private MissionHistoryRepository missionHistoryRepository;

	@Transactional
	public Mission createMission(MissionRequestDto dto) {
		return missionRepository.save(dto.toEntity());
	}

	public List<Mission> getMissionList() {
		return missionRepository.findAll();
	}

	public Mission getMission(long id) {
		return missionRepository.findById(id)
				.orElseThrow(() -> new MissionNotFoundException(ErrorType.MISSION, "ID에 해당하는 Mission을 찾을 수 없습니다."));
	}

	public List<Mission> getGroupMission(long groupId) {
		//그룹 존재?
		return missionRepository.findByGroupId(groupId);
	}

	/* public List<Mission> getUserMission(long userId) {
		return missionRepository.findByUserId(userId);
	} */

	@Transactional
	public void deleteMission(long id) {
		missionRepository.delete(getMission(id));
	}

	@Transactional
	public Mission updateMission(long id, MissionRequestDto dto) {
		Mission mission = getMission(id);

		return mission.update(dto.toEntity());
	}

	public void getMissionTime(long missionId) {

	}

	/* public void updateMissionTime(long missionId, long userId) {
		missionHistoryRepository.findByMissionAndUserId(missionId, userId);
	} */
}