package com.nexters.wiw.api.web;

import com.nexters.wiw.api.ui.GroupPageResponseDto;
import com.nexters.wiw.api.ui.MissionPageResponseDto;
import com.nexters.wiw.api.ui.MissionResponseDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import com.nexters.wiw.api.domain.Mission;
import com.nexters.wiw.api.service.MissionService;
import com.nexters.wiw.api.ui.MissionRequestDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
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

    private static final int PAGE_SIZE = 3;

    private MissionService missionService;

    //그룹 진행중, 진행 완료 미션
    @GetMapping(value="/group/{id}/missions")
    public ResponseEntity<PagedResources<MissionResponseDto>>getGroupMission(@RequestHeader("Authorization") String authHeader,
                                                                             @PageableDefault(size = 3, sort = "estimate") Pageable pageable,
                                                                             @PathVariable(name = "id") long groupId,
                                                                             @RequestParam(value = "end", required = false, defaultValue = "0") int end) {
        Page<Mission> missions;
        if(end == 0) missions = missionService.getGroupMission(authHeader, groupId, pageable);
        else missions = missionService.getGroupEndMission(authHeader, groupId, pageable);

        PagedResources.PageMetadata pageMetadata = new PagedResources.PageMetadata(
                missions.getSize(),
                missions.getNumber(),
                missions.getTotalElements(),
                missions.getTotalPages());
        PagedResources<MissionResponseDto> result =new PagedResources<>(
                MissionResponseDto.ofList(missions.getContent()), pageMetadata);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //진행 중인 유저 미션
    @GetMapping(value="/user/{id}/missions")
    public ResponseEntity<MissionPageResponseDto> getUserMission(@RequestHeader("Authorization") String authHeader,
                                                                             @RequestParam(value = "sort", required = false, defaultValue = "new") String sort,
                                                                             @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                             @PathVariable(name = "id") long userId) {
        Pageable pageable = sort.equals("new") ? PageRequest.of(page, PAGE_SIZE, new Sort(Sort.Direction.DESC,"created"))
                :  PageRequest.of(page, PAGE_SIZE, new Sort(Sort.Direction.ASC,"id"));

        //TODO: Native Query에 대한 페이징이 되지 않아 페이징 방식을 바꿈

        MissionPageResponseDto result = missionService.getUserMission(authHeader, userId, pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
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
}
