package com.nexters.wiw.api.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.service.GroupService;
import com.nexters.wiw.api.ui.GroupRequestDto;

import com.nexters.wiw.api.ui.GroupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public ResponseEntity<GroupResponseDto> create(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody @Valid GroupRequestDto groupRequestDto){
        Group group = groupService.save(authHeader, groupRequestDto);
        return new ResponseEntity<>(GroupResponseDto.of(group), HttpStatus.CREATED);
    }

    @PatchMapping("groups/{id}")
    public ResponseEntity<GroupResponseDto> update(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable Long id, @RequestBody @Valid GroupRequestDto groupRequestDto) {
        Group group = groupService.update(authHeader, id, groupRequestDto);
        return new ResponseEntity<>(GroupResponseDto.of(group), HttpStatus.OK);
    }

    @DeleteMapping("groups/{id}")
    public ResponseEntity<Void> delete(@RequestHeader("Authorization") String authHeader,
                                       @PathVariable Long id) {
        groupService.delete(authHeader, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("group")
    public ResponseEntity<GroupResponseDto> getGroup(@RequestHeader("Authorization") String authHeader,
                                          @RequestParam(value = "id") Long id) {
        Group group = groupService.getGroupById(authHeader, id);
        return new ResponseEntity<>(GroupResponseDto.of(group), HttpStatus.OK);
    }

    @GetMapping("groups")
    public ResponseEntity<Page<GroupResponseDto>> getGroups(@RequestHeader("Authorization") String authHeader,
                                                            final Pageable pageable,
                                                            @RequestParam(value = "name", required = false) String name,
                                                            @RequestParam(value = "category", required = false) String category) {
        Map<String, Object> filter = new HashMap<>();

        if(name != null) filter.put("name", name);
        if(category != null) filter.put("category", category);

        Page<GroupResponseDto> groups = groupService.getGroupByFilter(authHeader, pageable, filter);


        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("groups/user{id}")
    public ResponseEntity<List<GroupResponseDto>> getGroupsByUserId(@RequestHeader("Authorization") String authHeader,
                                                                    @PathVariable Long id) {
        List<Group> groups = groupService.getGroupByUserId(authHeader, id);
        return new ResponseEntity<>(GroupResponseDto.ofList(groups), HttpStatus.OK);
    }
}
