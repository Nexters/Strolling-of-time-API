package com.nexters.wiw.api.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

import com.nexters.wiw.api.domain.Mission;
import com.nexters.wiw.api.service.MissionService;
import com.nexters.wiw.api.ui.MissionRequestDto;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
        return missionService.getMissionList();
    }

    //미션 생성
    @PostMapping(value = "/mission")
    public ResponseEntity createMission(@RequestBody @Valid MissionRequestDto dto) {
        log.info(LocalDateTime.now() + " 미션 생성");

        return ResponseEntity.status(HttpStatus.CREATED).body(missionService.createMission(dto));
    }

    //나의 미션
    /* @GetMapping(value="/missions")
    public SomeData getMethodName(@RequestParam String param) {
        return new SomeData();
    } */

    //그룹 미션
    @GetMapping(value="/group/{groupId}/missions")
    public List<Mission> getGroupMission(@PathVariable long groupId) {
        return missionService.getGroupMission(groupId);
    }

    //미션 detail
    /* @GetMapping(value="/mission/{missionId}")
    public SomeData getMethodName(@RequestParam String param) {
        return new SomeData();
    } */

    //미션 삭제
    @DeleteMapping(value = "/mission/{id}")
    public ResponseEntity deleteMission(@PathVariable long id) {
        log.info(LocalDateTime.now() + " " + id + " 미션 삭제");
        missionService.deleteMission(id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //미션 수정
    @PutMapping(value="/mission/{id}")
    public ResponseEntity updateMission(@PathVariable long id, @RequestBody @Valid MissionRequestDto dto) {
        Mission updateMission = missionService.updateMission(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(updateMission);
    }
}