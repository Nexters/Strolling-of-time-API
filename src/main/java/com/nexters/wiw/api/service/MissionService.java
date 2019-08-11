package com.nexters.wiw.api.service;

import java.util.List;

import com.nexters.wiw.api.domain.Mission;
import com.nexters.wiw.api.domain.MissionRepository;
import com.nexters.wiw.api.exception.MissionNotFoundException;
import com.nexters.wiw.api.ui.MissionRequestDto;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MissionService {

	private MissionRepository missionRepository;

	@Transactional
	public Mission createMission(MissionRequestDto dto) {
		return missionRepository.save(dto.toEntity());
	}

	public List<Mission> getMissionList() {
		return missionRepository.findAll();
	}

	public Mission getMission(long id) {
		return missionRepository.findById(id)
				.orElseThrow(() -> new MissionNotFoundException("invalid mission id: " + id));
	}

	public List<Mission> getGroupMission(long groupId) {
		return missionRepository.findByGroupId(groupId);
	}

	@Transactional
	public void deleteMission(long id) {
		missionRepository.deleteById(id);
	}

	@Transactional
	public Mission updateMission(long id, MissionRequestDto dto) {
		Mission mission = getMission(id);

		return mission.update(dto.toEntity());
	}
}