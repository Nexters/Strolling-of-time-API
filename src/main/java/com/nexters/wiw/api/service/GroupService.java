package com.nexters.wiw.api.service;

import java.util.List;

import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.domain.GroupRepository;
import com.nexters.wiw.api.exception.GroupNotFoundException;
import com.nexters.wiw.api.ui.GroupRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;
    
    @Transactional
    public Group save(GroupRequestDto groupRequestDto) {
        Group group = groupRequestDto.toEntity();
        groupRepository.save(group);

        return group;
    }

    @Transactional
    public Group update(Long id, GroupRequestDto groupRequestDto) {
        Group origin = getGroupById(id);
        Group updated = origin.update(groupRequestDto.toEntity());

        return updated;
    }

    @Transactional
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }

    public List<Group> getAllgroup() {
        return groupRepository.findAll();
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

    public List<Group> getGroupByName(String keyword) {
        return groupRepository.findByNameContaining(keyword).orElseThrow(GroupNotFoundException::new);
    }
}