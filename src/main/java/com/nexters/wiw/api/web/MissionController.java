package com.nexters.wiw.api.web;

import com.nexters.wiw.api.service.AuthService;
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
    private AuthService authService;

    //전체 미션 리스트
    @GetMapping(value = "/missions")
    public List<Mission> getMissionList() {
        return missionService.getMissionList();
    }

    //그룹 미션 리스트
    @GetMapping(value="/group/{groupId}/missions")
    public List<Mission> getGroupMission(@PathVariable("groupId") final long groupId) {
        return missionService.getGroupMission(groupId);
    }

    //TODO 유저 미션 리스트 수정하기
    //토큰 받는다 -> 비교 -> exception or return mission list
    /* @GetMapping(value="/missions")
    public List<Mission> getUserMission(@RequestHeader("Authorization") String authHeader) {
        //유저가 가지고 있는 그룹 목록 모두 가져오기 -> 그 그룹에 속한 미션들 모두 가져오기
        long userId = authService.findIdByToken(authHeader);

        return missionService.getUserMission(userId);
    } */

    //TODO 미션 detail
    /* @GetMapping(value="/mission/{id}")
    public SomeData getMethodName() {
        return new SomeData();
    } */

    //그룹 미션 생성
    @PostMapping(value = "/group/{groupId}/mission")
    public ResponseEntity createMission(@PathVariable long groupId, @RequestBody @Valid MissionRequestDto dto) {
        Mission createMission = missionService.createMission(groupId, dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //미션 삭제
    @DeleteMapping(value = "/mission/{id}")
    public ResponseEntity deleteMission(@PathVariable long id) {
        missionService.deleteMission(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //미션 수정
    @PatchMapping(value = "/mission/{id}")
    public ResponseEntity updateMission(@PathVariable long id, @RequestBody @Valid MissionRequestDto dto) {
        Mission updateMission = missionService.updateMission(id, dto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
