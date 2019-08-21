package com.nexters.wiw.api.web;

import com.nexters.wiw.api.domain.MissionHistory;
import com.nexters.wiw.api.service.AuthService;
import com.nexters.wiw.api.service.MissionHistoryService;
import com.nexters.wiw.api.ui.MissionHistoryRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/api/v1/mission/{id}/times", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@AllArgsConstructor
@Slf4j
@RestController
public class MissionHistoryController {

    private MissionHistoryService missionHistoryService;
    private AuthService authService;

    //미션 누적 시간
    @GetMapping
    public ResponseEntity<MissionHistory> getMissionTime(@RequestHeader("Authorization") String authHeader,
                                                                   @PathVariable(name = "id") long missionId) {
        long userId = authService.findIdByToken(authHeader);
        MissionHistory history = missionHistoryService.getMissionTime(missionId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(history);
    }

    //미션 누적 시간 insert or update
    @PostMapping
    public ResponseEntity createMissionTime(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable(name = "id") long missionId,
                                            @RequestBody @Valid MissionHistoryRequestDto dto) {
        missionHistoryService.createMissionTime(missionId, authHeader, dto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
