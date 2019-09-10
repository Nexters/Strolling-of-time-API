package com.nexters.wiw.api.web;

import javax.validation.Valid;

import com.nexters.wiw.api.common.Auth;
import com.nexters.wiw.api.domain.GroupNotice;
import com.nexters.wiw.api.service.GroupNoticeService;
import com.nexters.wiw.api.ui.GroupNoticeResponseDto;
import com.nexters.wiw.api.ui.NoticeRequestDto;

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
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/api/v1/")
public class NoticeController {

    @Autowired
    GroupNoticeService groupNoticeService;

    @PostMapping("notices")
    @ApiOperation(value = "공지 생성", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<GroupNoticeResponseDto> create(@Auth Long userId,
            @RequestBody @Valid NoticeRequestDto groupNoticeRequestDto) {
        GroupNotice groupNotice = groupNoticeService.save(userId, groupNoticeRequestDto);
        return new ResponseEntity<GroupNoticeResponseDto>(GroupNoticeResponseDto.of(groupNotice), HttpStatus.CREATED);
    }

    @PatchMapping("notices/{id}")
    @ApiOperation(value = "공지 수정", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<GroupNoticeResponseDto> update(@Auth Long userId, @PathVariable Long id,
            @RequestBody @Valid NoticeRequestDto groupNoticeRequestDto) {
        GroupNotice updated = groupNoticeService.update(id, groupNoticeRequestDto);
        return new ResponseEntity<GroupNoticeResponseDto>(GroupNoticeResponseDto.of(updated), HttpStatus.OK);
    }

    @DeleteMapping("notices/{id}")
    @ApiOperation(value = "공지 삭제", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<Void> delete(@Auth Long userId, @PathVariable Long id) {
        groupNoticeService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("notices/{id}")
    @ApiOperation(value = "공지 상세보기", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<GroupNoticeResponseDto> getGroupNotice(@Auth Long userId, @PathVariable Long id) {
        GroupNoticeResponseDto result = GroupNoticeResponseDto.of(groupNoticeService.getGroupNoticeById(id));

        return new ResponseEntity<GroupNoticeResponseDto>(result, HttpStatus.OK);
    }

}
