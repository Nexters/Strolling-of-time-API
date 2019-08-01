package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.domain.GroupRepository;
import com.nexters.wiw.api.ui.GroupRequestDto;
import com.nexters.wiw.api.ui.GroupResponseDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;
    
    @Transactional
    public GroupResponseDto save(GroupRequestDto groupRequestDto) {
        Group savedGroup = groupRepository.save(groupRequestDto.of());
        return new GroupResponseDto();
    }
}