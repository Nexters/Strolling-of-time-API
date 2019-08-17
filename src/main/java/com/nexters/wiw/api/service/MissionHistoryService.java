package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.MissionHistory;
import com.nexters.wiw.api.domain.MissionHistoryRepository;
import com.nexters.wiw.api.ui.MissionHistoryRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@AllArgsConstructor
public class MissionHistoryService {

    private MissionHistoryRepository missionHistoryRepository;

    @Transactional
    public void updateMissionTime(long missionId, long userId, MissionHistoryRequestDto dto) {
        MissionHistory time = getMissionTime(missionId, userId);

        //미션 히스토리 가져오기 -> 있음? update 없음? insert

        time.update(dto.toEntity());
    }

    public MissionHistory getMissionTime(long missionId, long userId) {
        return missionHistoryRepository.findByMissionIdAndUserId(missionId, userId);
    }

    public void insertMissionTime(long missionId, long userId, MissionHistoryRequestDto dto) {
        MissionHistory newMissionHistory = dto.toEntity();

        //new

        //missionHistoryRepository.save(newMissionHistory);
    }
}
