package com.nexters.wiw.api.web;

import java.util.List;

import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.service.GroupService;
import com.nexters.wiw.api.ui.GroupRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping("groups")
    public ResponseEntity<Group> create(@RequestBody @Valid GroupRequestDto groupRequestDto){
        Group group = groupService.save(groupRequestDto);
        return new ResponseEntity<Group>(group, HttpStatus.CREATED);
    }

    @PatchMapping("groups/{id}")
    public ResponseEntity<Group> update(@PathVariable Long id, @RequestBody @Valid GroupRequestDto groupRequestDto) {
        Group updated = groupService.update(id, groupRequestDto);
        return new ResponseEntity<Group>(updated, HttpStatus.OK);
    }

    @DeleteMapping("groups/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("group")
    public ResponseEntity<Group> getGroup(@RequestParam(value = "id") Long id) {
        Group group = groupService.getGroupById(id);
        return new ResponseEntity<Group>(group, HttpStatus.OK);
    }

    @GetMapping("groups")
    public ResponseEntity<List<Group>> getGroups(@RequestParam(value = "keyword", required = false) String keyword) {
        List<Group> result;

        if (keyword != null) {
            result = groupService.getGroupByName(keyword);
        } else {
            result = groupService.getAllgroup();
        }

        if(result.size() == 0) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else return new ResponseEntity<List<Group>>(result, HttpStatus.OK);
    }
}
