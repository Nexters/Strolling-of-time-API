package com.nexters.wiw.api.web;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.nexters.wiw.api.common.Auth;
import com.nexters.wiw.api.domain.GroupNotice;
import com.nexters.wiw.api.service.GroupNoticeService;
import com.nexters.wiw.api.ui.GroupNoticeRequestDto;
import com.nexters.wiw.api.ui.GroupNoticeResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping("/api/v1/")
public class GroupNoticeController {

    @Autowired
    GroupNoticeService groupNoticeService;

    @PostMapping("group/{id}/group-notice")
    @ApiOperation(value = "그룹 공지 생성", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<GroupNoticeResponseDto> create(@Auth Long userId, @PathVariable Long groupId,
            @RequestBody @Valid GroupNoticeRequestDto groupNoticeRequestDto) {
        GroupNotice groupNotice = groupNoticeService.save(userId, groupId, groupNoticeRequestDto);
        return new ResponseEntity<GroupNoticeResponseDto>(GroupNoticeResponseDto.of(groupNotice), HttpStatus.CREATED);
    }

    @PatchMapping("group-notice/{id}")
    @ApiOperation(value = "그룹 공지 수정", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<GroupNoticeResponseDto> update(@Auth Long userId, @PathVariable Long id,
            @RequestBody @Valid GroupNoticeRequestDto groupNoticeRequestDto) {
        GroupNotice updated = groupNoticeService.update(id, groupNoticeRequestDto);
        return new ResponseEntity<GroupNoticeResponseDto>(GroupNoticeResponseDto.of(updated), HttpStatus.OK);
    }

    @DeleteMapping("group-notice/{id}")
    @ApiOperation(value = "그룹 공지 삭제", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<Void> delete(@Auth Long userId, @PathVariable Long id) {
        groupNoticeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("group-notice/{id}")
    @ApiOperation(value = "그룹 공지 상세보기", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<GroupNoticeResponseDto> getGroupNotice(@Auth Long userId, @PathVariable Long id) {
        GroupNoticeResponseDto result = GroupNoticeResponseDto.of(groupNoticeService.getGroupNoticeById(id));

        return new ResponseEntity<GroupNoticeResponseDto>(result, HttpStatus.OK);
    }

    @GetMapping("group/{id}/notices")
    @ApiOperation(value = "그룹 공지 리스트 불러오기", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<List<GroupNoticeResponseDto>> getGroupNoticeByGroupId(@Auth Long userId,
            @PathVariable Long id, @RequestParam(value = "keyword", required = false) String keyword) {
        List<GroupNoticeResponseDto> result = new ArrayList<>();

        if (keyword != null) {
            for (GroupNotice groupNotice : groupNoticeService.getGroupNoticeByTitle(keyword)) {
                result.add(GroupNoticeResponseDto.of(groupNotice));
            }
        } else {
            for (GroupNotice groupNotice : groupNoticeService.getGroupByGroupId(id)) {
                result.add(GroupNoticeResponseDto.of(groupNotice));
            }
        }

        if (result.size() == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<GroupNoticeResponseDto>>(result, HttpStatus.OK);
    }

    @GetMapping("user/{id}/notices")
    @ApiOperation(value = "유저가 작성한 공지 보기", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<List<GroupNoticeResponseDto>> getGroupNoticeByUserId(@Auth Long userId,
            @PathVariable Long id) {
        List<GroupNoticeResponseDto> result = new ArrayList<>();

        for (GroupNotice groupNotice : groupNoticeService.getGroupByUserId(id)) {
            result.add(GroupNoticeResponseDto.of(groupNotice));
        }

        if (result.size() == 0)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<List<GroupNoticeResponseDto>>(result, HttpStatus.OK);
    }
}
