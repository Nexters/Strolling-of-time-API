package com.nexters.wiw.api.web;

import com.nexters.wiw.api.domain.Mission;
import com.nexters.wiw.api.domain.MissionHistory;
import com.nexters.wiw.api.service.AuthService;
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
    private AuthService authService;

    //TODO 미션 누적 시간
    @GetMapping
    public MissionHistory getMissionTime(@RequestHeader("Authorization") String authHeader,
                                         @PathVariable long missionId) {
        long userId = authService.findIdByToken(authHeader);

        return missionHistoryService.getMissionTime(missionId, userId);
    }

    //미션 누적시간 기록
    //insert and update
    /* @PutMapping
    public void updateMissionTime(@RequestHeader("Authorization") String authHeader,
                                  @PathVariable long missionId,
                                  @RequestBody @Valid MissionHistoryRequestDto dto) {

        //time replace, 수정 시간(마지막 update 시간)
        long userId = authService.findIdByToken(authHeader);
        missionHistoryService.updateMissionTime(missionId, userId, dto);
    } */

    @PostMapping
    public void createMissionTime(@RequestHeader("Authorization") String authHeader,
                                  @PathVariable long missionId,
                                  @RequestBody @Valid MissionHistoryRequestDto dto) {
        //time replace, 수정 시간(마지막 update 시간)
        //유저 + 미션 번호 = 복합
        missionHistoryService.createMissionTime(missionId, authHeader, dto);
    }
}
