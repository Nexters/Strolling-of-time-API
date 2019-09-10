package com.nexters.wiw.api.web;

import javax.validation.Valid;
import com.nexters.wiw.api.common.Auth;
import com.nexters.wiw.api.domain.MissionHistory;
import com.nexters.wiw.api.service.MissionHistoryService;
import com.nexters.wiw.api.service.MissionService;
import com.nexters.wiw.api.ui.MissionHistoryRequestDto;
import com.nexters.wiw.api.ui.MissionPageResponseDto;
import com.nexters.wiw.api.ui.MissionRequestDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MissionController {
    final int PAGE_SIZE = 3;
    final MissionService missionService;
    final MissionHistoryService missionHistoryService;

    // 진행 중인 유저 미션
    @GetMapping(value = "/missions")
    @ApiOperation(value = "미션 가져오기", authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<MissionPageResponseDto> getUserMission(@Auth Long userId,
            @RequestParam(value = "sort", required = false, defaultValue = "new") String sort,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page) {
        Pageable pageable = sort.equals("new")
                ? PageRequest.of(page, PAGE_SIZE, new Sort(Sort.Direction.DESC, "created"))
                : PageRequest.of(page, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));

        // TODO: Native Query에 대한 페이징이 되지 않아 페이징 방식을 바꿈

        MissionPageResponseDto result = missionService.getUserMission(userId, pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // 미션 삭제
    @DeleteMapping(value = "/missions/{id}")
    @ApiOperation(value = " 미션 삭제", authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity deleteMission(@Auth Long userId, @PathVariable long id) {
        missionService.deleteMission(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 미션 수정
    @PatchMapping(value = "/missions/{id}")
    @ApiOperation(value = "미션 수정", authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity updateMission(@Auth Long userId, @PathVariable long id,
            @RequestBody @Valid MissionRequestDto dto) {
        missionService.updateMission(id, dto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 미션 누적 시간
    @GetMapping(value = "/missions/{id}/times")
    @ApiOperation(value = "미션 누적 시간 확인", authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<MissionHistory> getMissionTime(@Auth Long userId,
            @PathVariable(name = "id") long missionId) {
        MissionHistory history = missionHistoryService.getMissionTime(missionId, userId);

        return ResponseEntity.status(HttpStatus.OK).body(history);
    }

    // 미션 누적 시간 insert or update
    @PostMapping(value = "/missions/{id}/times")
    @ApiOperation(value = "미션 누적 시간 저장", authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<Void> createMissionTime(@Auth Long userId,
            @PathVariable(name = "id") long missionId,
            @RequestBody @Valid MissionHistoryRequestDto dto) {
        missionHistoryService.createMissionTime(missionId, userId, dto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
