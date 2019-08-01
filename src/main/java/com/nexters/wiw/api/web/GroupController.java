package com.nexters.wiw.api.web;

import java.net.URI;
import java.time.LocalDateTime;

import com.nexters.wiw.api.service.GroupService;
import com.nexters.wiw.api.ui.GroupRequestDto;
import com.nexters.wiw.api.ui.GroupResponseDto;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: RequestMapping이 필요한가?
@RestController
@Slf4j
@RequiredArgsConstructor
public class GroupController {

    private GroupService groupService;

    @PostMapping("groups")
    public ResponseEntity<GroupResponseDto> createGroup(@RequestBody GroupRequestDto groupRequestDto){
        log.info("[" + LocalDateTime.now() + "] 그룹 생성");
        GroupResponseDto groupResponseDto = groupService.save(groupRequestDto);
        return ResponseEntity.created(URI.create("124")).body(groupResponseDto);
        //FIXME: URI.create("여기에 들어갈거 고치기")
    }
}