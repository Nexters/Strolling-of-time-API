package com.nexters.wiw.api.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import com.nexters.wiw.api.common.Auth;
import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.domain.GroupNotice;
import com.nexters.wiw.api.domain.Mission;
import com.nexters.wiw.api.service.GroupMemeberService;
import com.nexters.wiw.api.service.GroupNoticeService;
import com.nexters.wiw.api.service.GroupService;
import com.nexters.wiw.api.service.MissionService;
import com.nexters.wiw.api.ui.GroupNoticeResponseDto;
import com.nexters.wiw.api.ui.GroupPageResponseDto;
import com.nexters.wiw.api.ui.GroupRequestDto;
import com.nexters.wiw.api.ui.GroupResponseDto;
import com.nexters.wiw.api.ui.MissionResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupController {

    private static final int PAGE_SIZE = 3;

    final GroupService groupService;
    final GroupNoticeService groupNoticeService;
    final GroupMemeberService groupMemeberService;
    final MissionService missionService;

    @PostMapping("groups")
    @ApiOperation(value = "그룹 생성", authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<GroupResponseDto> create(@Auth Long userId,
            @RequestBody @Valid GroupRequestDto groupRequestDto) {
        Group group = groupService.save(userId, groupRequestDto);
        return new ResponseEntity<>(GroupResponseDto.of(group), HttpStatus.CREATED);
    }

    @PatchMapping("groups/{id}")
    @ApiOperation(value = "그룹 수정", authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<GroupResponseDto> update(@Auth Long userId, @PathVariable Long id,
            @RequestBody @Valid GroupRequestDto groupRequestDto) {
        Group group = groupService.update(id, groupRequestDto);
        return new ResponseEntity<>(GroupResponseDto.of(group), HttpStatus.OK);
    }

    @DeleteMapping("groups/{id}")
    @ApiOperation(value = "그룹 삭제", authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<Void> delete(@Auth Long userId, @PathVariable Long id) {
        groupMemeberService.leaveGroup(userId, id);
        groupService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("groups/{id}")
    @ApiOperation(value = "그룹 상세보기", authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<GroupResponseDto> getGroup(@Auth Long userId, @PathVariable Long id) {
        Group group = groupService.getGroupById(id);
        return new ResponseEntity<>(GroupResponseDto.of(group), HttpStatus.OK);
    }

    @GetMapping("groups/{id}/notices")
    @ApiOperation(value = "그룹 공지 리스트 불러오기", authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<List<GroupNoticeResponseDto>> getGroupNoticeByGroupId(@Auth Long userId,
            @PathVariable Long id,
            @RequestParam(value = "keyword", required = false) String keyword) {
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

    // 그룹 진행중, 진행 완료 미션
    @GetMapping(value = "groups/{id}/missions")
    @ApiOperation(value = "그룹 미션 리스트 불러오기", authorizations = {@Authorization(value = "apiKey")})
    public ResponseEntity<PagedResources<MissionResponseDto>> getGroupMission(@Auth Long userId,
            @PageableDefault(size = 3, sort = "estimate") Pageable pageable,
            @PathVariable(name = "id") long groupId,
            @RequestParam(value = "end", required = false, defaultValue = "0") int end) {
        Page<Mission> missions;
        if (end == 0)
            missions = missionService.getGroupMission(groupId, pageable);
        else
            missions = missionService.getGroupEndMission(groupId, pageable);

        PagedResources.PageMetadata pageMetadata =
                new PagedResources.PageMetadata(missions.getSize(), missions.getNumber(),
                        missions.getTotalElements(), missions.getTotalPages());
        PagedResources<MissionResponseDto> result = new PagedResources<>(
                MissionResponseDto.ofList(missions.getContent()), pageMetadata);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("groups")
    @ApiOperation(value = "그룹 리스트", authorizations = {@Authorization(value = "apiKey")})
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
