package com.nexters.wiw.api.web;

import com.nexters.wiw.api.domain.MissionHistory;
import com.nexters.wiw.api.service.AuthService;
import com.nexters.wiw.api.service.MissionHistoryService;
import com.nexters.wiw.api.ui.MissionHistoryRequestDto;
import com.nexters.wiw.api.ui.MissionHistoryResponseDto;
import com.nexters.wiw.api.ui.MissionResponseDto;
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

    @GetMapping
    public ResponseEntity<MissionHistoryResponseDto> getMissionTime(@RequestHeader("Authorization") String authHeader,
                                                                    @PathVariable(name = "id") long missionId) {
        MissionHistory history = missionHistoryService.getMissionTime(missionId, authHeader);
        MissionHistoryResponseDto result = new MissionHistoryResponseDto(history);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity createMissionTime(@RequestHeader("Authorization") String authHeader,
                                            @PathVariable(name = "id") long missionId,
                                            @RequestBody @Valid MissionHistoryRequestDto dto) {
        missionHistoryService.createMissionTime(missionId, authHeader, dto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
