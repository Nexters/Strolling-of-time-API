package com.nexters.wiw.api.web;

import java.time.LocalDateTime;

import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.service.GroupService;
import com.nexters.wiw.api.ui.GroupRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("groups")
    public ResponseEntity create(@RequestBody GroupRequestDto groupRequestDto){
        log.info("[" + LocalDateTime.now() + "] 그룹 생성");
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.save(groupRequestDto));
    } 

    @GetMapping("groups")
    public ResponseEntity getAllGroups() {
        log.info("[" + LocalDateTime.now() + "] 그룹 조회");
        return ResponseEntity.ok().body(groupService.list());
    }

    @PutMapping("groups/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody GroupRequestDto groupRequestDto) {
        log.info("[" + LocalDateTime.now() + "] 그룹 수정");
        Group updatedGroup = groupService.updateGroup(id, groupRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedGroup);
    }
    
    @DeleteMapping("groups/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        log.info("[" + LocalDateTime.now() + "] 그룹 삭제");
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }
}