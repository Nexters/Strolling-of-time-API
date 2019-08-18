package com.nexters.wiw.api.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import com.nexters.wiw.api.domain.Mission;
import com.nexters.wiw.api.service.MissionService;
import com.nexters.wiw.api.ui.MissionRequestDto;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.Valid;

@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@AllArgsConstructor
@Slf4j
@RestController
public class MissionController {

    private MissionService missionService;

    //전체 미션 리스트
    @GetMapping(value = "/missions")
    public List<Mission> getMissionList() {
        return missionService.getMissionList();
    }

    //그룹 진행중, 진행 완료 미션
    @GetMapping(value="/group/{id}/missions")
    public ResponseEntity<List<Mission>> getGroupMission(@RequestHeader("Authorization") String authHeader,
                                         @PathVariable(name = "id") long groupId,
                                         @RequestParam(value = "end", required = false, defaultValue = "0") int end) {
        List<Mission> missions;
        if(end == 0)
            missions = missionService.getGroupMission(authHeader, groupId);
        else
            missions = missionService.getGroupEndMission(authHeader, groupId);

        return ResponseEntity.status(HttpStatus.OK).body(missions);
    }

    //유저가 속한 그룹의 진행중 미션
    /* @GetMapping(value="/missions")
    public List<Mission> getUserMission(@RequestHeader("Authorization") String authHeader) {
        long userId = authService.findIdByToken(authHeader);

        return missionService.getUserMission(userId);
    } */

    //그룹 미션 생성
    @PostMapping(value = "/group/{id}/mission")
    public ResponseEntity createMission(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable(name = "id") long groupId,
                                        @RequestBody @Valid MissionRequestDto dto) {
        missionService.createMission(authHeader, groupId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //미션 삭제
    @DeleteMapping(value = "/mission/{id}")
    public ResponseEntity deleteMission(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable long id) {
        missionService.deleteMission(authHeader, id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //미션 수정
    @PatchMapping(value = "/mission/{id}")
    public ResponseEntity updateMission(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable long id, @RequestBody @Valid MissionRequestDto dto) {
        missionService.updateMission(authHeader, id, dto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
