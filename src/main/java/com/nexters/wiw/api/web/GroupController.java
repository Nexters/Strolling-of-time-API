package com.nexters.wiw.api.web;

import java.util.List;

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

@RestController
@RequiredArgsConstructor
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("groups")
    public ResponseEntity<Void> create(@RequestBody GroupRequestDto groupRequestDto){
        groupService.save(groupRequestDto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    } 

    @GetMapping("groups")
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groupList = groupService.getAllgroup();
        return new ResponseEntity<List<Group>>(groupList, HttpStatus.OK);
    }

    @GetMapping("groups/{id}")
    public ResponseEntity<Group> getGroup(@PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        return new ResponseEntity<Group>(group, HttpStatus.OK);
    }

    @GetMapping("groups/{keyword}")
    public ResponseEntity<List<Group>> getGroup(@PathVariable String keyword) {
        List<Group> groupList = groupService.getGroupByName(keyword);
        return new ResponseEntity<List<Group>>(groupList, HttpStatus.OK);
    }

    @PutMapping("groups/{id}")
    public ResponseEntity<Group> update(@PathVariable Long id, @RequestBody GroupRequestDto groupRequestDto) {
        Group updatedGroup = groupService.updateGroup(id, groupRequestDto);
        return new ResponseEntity<Group>(updatedGroup, HttpStatus.OK);
    }
    
    @DeleteMapping("groups/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.deleteGroup(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}