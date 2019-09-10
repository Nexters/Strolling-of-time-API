package com.nexters.wiw.api.service;

import java.time.LocalDateTime;
import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.domain.Mission;
import com.nexters.wiw.api.domain.MissionRepository;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.MissionNotFoundException;
import com.nexters.wiw.api.ui.MissionPageResponseDto;
import com.nexters.wiw.api.ui.MissionRequestDto;
import com.nexters.wiw.api.ui.MissionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionService {
    final MissionRepository missionRepository;
    final GroupService groupService;

    @Transactional
    public Mission createMission(long groupId, MissionRequestDto dto) {
        Mission newMission = dto.toEntity();
        Group group = groupService.getGroupById(groupId);
        newMission.addGroup(group);

        return missionRepository.save(newMission);
    }

    public Mission getMission(long id) {
        return missionRepository.findById(id)
                .orElseThrow(() -> new MissionNotFoundException(ErrorType.NOT_FOUND,
                        "ID에 해당하는 Mission을 찾을 수 없습니다."));
    }

    public Page<Mission> getGroupMission(long groupId, Pageable pageable) {

        // 수행중인 미션, estimate 기준
        LocalDateTime now = LocalDateTime.now();
        Page<Mission> mission =
                missionRepository.findByGroupIdAndEstimateGreaterThan(groupId, now, pageable);

        // if(mission.isEmpty())
        // throw new MissionNotFoundException(ErrorType.NOT_FOUND, "진행 중인 Group Mission이
        // 존재하지 않습니다.");

        return mission;
    }

    public Page<Mission> getGroupEndMission(long groupId, Pageable pageable) {

        // 수행 완료 미션, estimate 기준
        LocalDateTime now = LocalDateTime.now();
        Page<Mission> mission =
                missionRepository.findByGroupIdAndEstimateLessThanEqual(groupId, now, pageable);

        if (mission.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND, "지난 Group Mission이 존재하지 않습니다.");

        return mission;
    }

    public MissionPageResponseDto getUserMission(long userId, Pageable pageable) {

        Page<MissionResponseDto> pages =
                missionRepository.findAllByUserId(userId, pageable).map(MissionResponseDto::new);

        // FIXME: Native Query로 바로 요청하기 때문에 user가 존재하는지 확인하지 못함, 따라서
        // UserNotFoundException은 발생하지 않는다.
        if (pages.isEmpty())
            throw new MissionNotFoundException(ErrorType.NOT_FOUND,
                    "진행 중인 User Mission이 존재하지 않습니다.");

        MissionPageResponseDto result = MissionPageResponseDto.builder().content(pages.getContent())
                .number(pages.getNumber()).size(pages.getSize())
                .totalElements(pages.getTotalElements()).totalPages(pages.getTotalPages()).build();

        return result;
    }

    @Transactional
    public void deleteMission(long id) {

        Mission mission = getMission(id);
        missionRepository.delete(mission);
    }

    @Transactional
    public Mission updateMission(long id, MissionRequestDto dto) {
        Mission mission = getMission(id);

        return mission.update(dto.toEntity());
    }
}
