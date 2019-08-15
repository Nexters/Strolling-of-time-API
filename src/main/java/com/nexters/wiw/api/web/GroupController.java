package com.nexters.wiw.api.web;

import java.util.ArrayList;
import java.util.List;

import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.service.GroupService;
import com.nexters.wiw.api.ui.GroupRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/group")
@RequiredArgsConstructor
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("groups")
    public ResponseEntity<Group> create(@RequestBody GroupRequestDto groupRequestDto){
        Group group = groupService.save(groupRequestDto);
        return new ResponseEntity<Group>(group, HttpStatus.CREATED);
    }

    @PutMapping("groups/{id}")
    public ResponseEntity<Group> update(@PathVariable Long id, @RequestBody GroupRequestDto groupRequestDto) {
        Group updated = groupService.updateGroup(id, groupRequestDto);
        return new ResponseEntity<Group>(updated, HttpStatus.OK);
    }

    @DeleteMapping("groups/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("groups")
    public ResponseEntity<List<Group>> getGroup(@RequestParam(value = "id", required = false) Long id,
                                          @RequestParam(value = "keyword", required = false) String keyword) {
        List<Group> result = new ArrayList<Group>();

        if(id != null) {
            Group group = groupService.getGroupById(id);
            result.add(group);
        } else if (keyword != null) {
            result = groupService.getGroupByName(keyword);
        } else {
            result = groupService.getAllgroup();
        }
        return new ResponseEntity<List<Group>>(result, HttpStatus.OK);
    }
}