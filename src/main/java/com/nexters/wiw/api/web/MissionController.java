package com.nexters.wiw.api.web;

import com.nexters.wiw.api.domain.User;
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

    //미션 리스트 
    @GetMapping(value = "/missions")
    public List<Mission> getMissionList() {
        //제목, 목표시간, 남은 시간
        return missionService.getMissionList();
    }

    //미션 생성
    @PostMapping(value = "/mission")
    public ResponseEntity createMission(@RequestBody @Valid MissionRequestDto dto) {
        Mission createMission = missionService.createMission(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createMission);
    }

    //나의 미션
    /* @GetMapping(value="/missions")
    public List<Mission> getUserMission(@PathVariable long userId) {
        return missionService.getUserMission(userId);
    } */

    //그룹 미션
    @GetMapping(value="/group/{groupId}/missions")
    public List<Mission> getGroupMission(@PathVariable long groupId) {
        return missionService.getGroupMission(groupId);
    }

    //미션 detail
    //유저 리스트, 소요시간, %
    /* @GetMapping(value="/mission/{missionId}")
    public SomeData getMethodName(@RequestParam String param) {
        //+ 참여자, 달성률
        return new SomeData();
    } */

    //미션 삭제
    @DeleteMapping(value = "/mission/{id}")
    public ResponseEntity deleteMission(@PathVariable long id) {
        missionService.deleteMission(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //미션 수정
    @PutMapping(value = "/mission/{id}")
    public ResponseEntity updateMission(@PathVariable long id, @RequestBody @Valid MissionRequestDto dto) {
        Mission updateMission = missionService.updateMission(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updateMission);
    }

    //TODO 미션 누적 시간
    @GetMapping(value = "/mission/{missionId}/times")
    public void getMissionTime(@PathVariable long missionId) {
        missionService.getMissionTime(missionId);
    }

    //TODO 미션 누적시간 기록
    /* @PutMapping(value = "/mission/{missionId}/times")
    public void updateMissionTime(@PathVariable long missionId, User user) {
        //time replace, 수정시간(마지막 update시간)
        //유저 + 미션 번호
        missionService.updateMissionTime(missionId, user.getId());
    } */
}