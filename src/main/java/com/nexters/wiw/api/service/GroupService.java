package com.nexters.wiw.api.service;

import java.util.List;
import java.util.Optional;


import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.domain.GroupRepository;
import com.nexters.wiw.api.ui.GroupRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    
    public Group save(GroupRequestDto groupRequestDto) {
        return groupRepository.save(groupRequestDto.toEntity());
    }

    public List<Group> list() {
        return groupRepository.findAll();
    }

    public Optional<Group> getGroup(Long id) { return groupRepository.findById(id); }

    @Transactional
    public Group updateGroup(Long id, GroupRequestDto groupRequestDto) {
        return getGroup(id).get().update(groupRequestDto.toEntity());
    }

    @Transactional
    public void deleteGroup(Long id) { groupRepository.deleteById(id); }
    
}