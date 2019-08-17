package com.nexters.wiw.api.web;

import java.util.ArrayList;
import java.util.List;

import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.service.GroupService;
import com.nexters.wiw.api.ui.GroupRequestDto;

import com.nexters.wiw.api.ui.GroupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("groups")
    public ResponseEntity<Group> create(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody @Valid GroupRequestDto groupRequestDto){
        Group group = groupService.save(authHeader, groupRequestDto);
        return new ResponseEntity<Group>(group, HttpStatus.CREATED);
    }

    @PatchMapping("groups/{id}")
    public ResponseEntity<Group> update(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable Long id, @RequestBody @Valid GroupRequestDto groupRequestDto) {
        Group updated = groupService.update(authHeader, id, groupRequestDto);
        return new ResponseEntity<Group>(updated, HttpStatus.OK);
    }

    @DeleteMapping("groups/{id}")
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String authHeader,
                                       @PathVariable Long id) {
        groupService.delete(authHeader, id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("group")
    public ResponseEntity<Group> getGroup(@RequestHeader("Authorization") String authHeader,
                                          @RequestParam(value = "id") Long id) {
        Group group = groupService.getGroupById(authHeader, id);
        return new ResponseEntity<Group>(group, HttpStatus.OK);
    }

    @GetMapping("groups")
    public ResponseEntity<List<GroupResponseDto>> getGroups(@RequestHeader("Authorization") String authHeader,
                                                            @RequestParam(value = "keyword", required = false) String keyword) {
//        List<Group> result;
//
//        if (keyword != null) {
//            result = groupService.getGroupByName(authHeader, keyword);
//        } else {
//            result = groupService.getAllgroup(authHeader);
//        }
//
//        if(result.size() == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        else return new ResponseEntity<List<Group>>(result, HttpStatus.OK);

        List<GroupResponseDto> result = new ArrayList<>();

        for(Group group : groupService.getAllgroup(authHeader)){
            result.add(GroupResponseDto.of(group));
        }

        return new ResponseEntity<List<GroupResponseDto>>(result, HttpStatus.OK);

    }
}
