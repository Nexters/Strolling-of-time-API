package com.nexters.wiw.api.web;

import com.nexters.wiw.api.domain.MissionHistory;
import com.nexters.wiw.api.service.MissionHistoryService;
import com.nexters.wiw.api.ui.MissionHistoryRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api/v1/mission/{missionId}/times", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@AllArgsConstructor
@Slf4j
@RestController
public class MissionHistoryController {

    private MissionHistoryService missionHistoryService;

    //TODO 미션 누적 시간
    @GetMapping
    public MissionHistory getMissionTime(@PathVariable long missionId) {
        //현재 로그인 중인 유저 id 가져오기
        long userId = 1;
        return missionHistoryService.getMissionTime(missionId, userId);
    }

    //TODO 미션 누적시간 기록
    //duplicate update -> SQLInsert?
    //insert and update
    @PutMapping
    public void updateMissionTime(@PathVariable long missionId, @RequestBody @Valid MissionHistoryRequestDto dto) {
        //time replace, 수정 시간(마지막 update 시간)
        //유저 + 미션 번호 = 복합
        long userId = 1;
        missionHistoryService.updateMissionTime(missionId, userId, dto);
    }
}
