package com.nexters.wiw.api.web;

import java.util.HashMap;
import java.util.Map;

import com.nexters.wiw.api.common.Auth;
import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.service.GroupMemeberService;
import com.nexters.wiw.api.service.GroupService;
import com.nexters.wiw.api.ui.GroupPageResponseDto;
import com.nexters.wiw.api.ui.GroupRequestDto;

import com.nexters.wiw.api.ui.GroupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
public class GroupController {

    private static final int PAGE_SIZE = 3;

    @Autowired
    private GroupService groupService;

    @Autowired
    private GroupMemeberService groupMemeberService;

    @PostMapping("groups")
    @ApiOperation(value = "그룹 생성", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<GroupResponseDto> create(@Auth Long userId,
            @RequestBody @Valid GroupRequestDto groupRequestDto) {
        Group group = groupService.save(userId, groupRequestDto);
        return new ResponseEntity<>(GroupResponseDto.of(group), HttpStatus.CREATED);
    }

    @PatchMapping("groups/{id}")
    @ApiOperation(value = "그룹 수정", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<GroupResponseDto> update(@Auth Long userId, @PathVariable Long id,
            @RequestBody @Valid GroupRequestDto groupRequestDto) {
        Group group = groupService.update(id, groupRequestDto);
        return new ResponseEntity<>(GroupResponseDto.of(group), HttpStatus.OK);
    }

    @DeleteMapping("groups/{id}")
    @ApiOperation(value = "그룹 삭제", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<Void> delete(@Auth Long userId, @PathVariable Long id) {
        groupMemeberService.leaveGroup(userId, id);
        groupService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("groups/{id}")
    @ApiOperation(value = "그룹 상세보기", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<GroupResponseDto> getGroup(@Auth Long userId, @PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        return new ResponseEntity<>(GroupResponseDto.of(group), HttpStatus.OK);
    }

    @GetMapping("groups")
    @ApiOperation(value = "그룹 리스트", authorizations = { @Authorization(value = "apiKey") })
    public ResponseEntity<GroupPageResponseDto> getGroups(@Auth Long userId,
            @RequestParam(value = "sort", required = false, defaultValue = "new") String sort,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category) {

        Pageable pageable = sort.equals("new")
                ? PageRequest.of(page, PAGE_SIZE, new Sort(Sort.Direction.DESC, "created"))
                : PageRequest.of(page, PAGE_SIZE, new Sort(Sort.Direction.ASC, "id"));

        Map<String, Object> filter = new HashMap<>();

        if (userId != null) {
            GroupPageResponseDto result = groupService.getGroupByUserId(pageable, userId);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        if (name != null)
            filter.put("name", name);
        if (category != null)
            filter.put("category", category);

        GroupPageResponseDto result = groupService.getGroupByFilter(pageable, filter);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
