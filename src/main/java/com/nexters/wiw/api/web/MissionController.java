package com.nexters.wiw.api.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

import com.nexters.wiw.api.domain.Mission;
import com.nexters.wiw.api.service.MissionService;
import com.nexters.wiw.api.ui.MissionRequestDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    /* @GetMapping(value = "/missions/all")
    public List<Mission> getMissionList() {
        return missionService.getMissionList();
    } */

    //그룹 진행중, 진행 완료 미션
    @GetMapping(value="/group/{id}/missions")
    public ResponseEntity<Page<Mission>> getGroupMission(@RequestHeader("Authorization") String authHeader,
                                                         @PageableDefault(size = 3, sort = "estimate") Pageable pageable,
                                                         @PathVariable(name = "id") long groupId,
                                                         @RequestParam(value = "end", required = false, defaultValue = "0") int end) {
        Page<Mission> missions;
        if(end == 0)
            missions = missionService.getGroupMission(authHeader, groupId, pageable);
        else
            missions = missionService.getGroupEndMission(authHeader, groupId, pageable);

        return ResponseEntity.status(HttpStatus.OK).body(missions);
    }

    //TODO 유저가 속한 그룹의 진행중 미션, 페이징 적용
    @GetMapping(value="/missions")
    public ResponseEntity<Map<String, List<Mission>>> getUserMission(@RequestHeader("Authorization") String authHeader,
                                                                     @PageableDefault(size = 3, sort = "estimate") Pageable pageable) {
        //유저가 속한 그룹의 pk값 - 임시
        long[] groupPk = {1, 2, 3};
        Map<String, List<Mission>> missions = missionService.getUserMission(authHeader, groupPk);

        return ResponseEntity.status(HttpStatus.OK).body(missions);
    }

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

    //TODO 미션 상태 변경 DOING -> DONE
    /* @PatchMapping(value = "/mission")
    public ResponseEntity updateMissionStatus() {
        return null;
    } */
}
