package com.nexters.wiw.api.service;

import java.util.Map;
import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.domain.GroupRepository;
import com.nexters.wiw.api.exception.GroupNotFoundException;
import com.nexters.wiw.api.service.specification.GroupSpecification;
import com.nexters.wiw.api.ui.GroupPageResponseDto;
import com.nexters.wiw.api.ui.GroupRequestDto;
import com.nexters.wiw.api.ui.GroupResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupService {

    final GroupRepository groupRepository;
    final GroupMemeberService groupMemeberService;

    @Transactional
    public Group save(Long userId, GroupRequestDto groupRequestDto) {

        Group group = GroupRequestDto.to(groupRequestDto);

        groupRepository.save(group);
        groupMemeberService.joinGroup(userId, group.getId());

        return group;
    }

    @Transactional
    public Group update(Long id, GroupRequestDto groupRequestDto) {
        Group origin = groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
        Group updated = origin.update(GroupRequestDto.to(groupRequestDto));

        return updated;
    }

    @Transactional
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

    public GroupPageResponseDto getGroupByUserId(Pageable pageable, Long id) {
        Page<GroupResponseDto> pages =
                groupRepository.findAllByUserId(id, pageable).map(GroupResponseDto::of);

        GroupPageResponseDto result = GroupPageResponseDto.builder().content(pages.getContent())
                .number(pages.getNumber()).size(pages.getSize())
                .totalElements(pages.getTotalElements()).totalPages(pages.getTotalPages()).build();

        return result;
    }

    public GroupPageResponseDto getGroupByFilter(Pageable pageable, Map<String, Object> filter) {

        Page<GroupResponseDto> pages;

        pages = groupRepository.findAll(GroupSpecification.search(filter), pageable)
                .map(GroupResponseDto::of);

        GroupPageResponseDto result = GroupPageResponseDto.builder().content(pages.getContent())
                .number(pages.getNumber()).size(pages.getSize())
                .totalElements(pages.getTotalElements()).totalPages(pages.getTotalPages()).build();

        return result;
    }
}
