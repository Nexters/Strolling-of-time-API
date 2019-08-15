package com.nexters.wiw.api.service;

import java.util.List;

import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.domain.GroupRepository;
import com.nexters.wiw.api.exception.GroupDuplicatedException;
import com.nexters.wiw.api.exception.GroupNotExistedException;
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
        if(isExistGroup(groupRequestDto.getName())) throw new GroupDuplicatedException();
        Group group = groupRequestDto.toEntity();
        groupRepository.save(group);

        return group;
    }

    @Transactional
    public Group updateGroup(Long id, GroupRequestDto groupRequestDto) {
        Group origin = getGroupById(id);
        Group updated = origin.update(groupRequestDto.toEntity());

        return updated;
    }

    @Transactional
    public void deleteGroup(Long id) {
        if(!isExistGroup(id)) throw new GroupNotExistedException();
        groupRepository.deleteById(id);
    }

    public List<Group> getAllgroup() {
        return groupRepository.findAll();
    }

    public Group getGroupById(Long id) {
        return groupRepository.findById(id).orElseThrow(GroupNotExistedException::new);
    }

    public List<Group> getGroupByName(String keyword) {
        return groupRepository.findByNameContaining(keyword).orElseThrow(GroupNotExistedException::new);
    }

    public boolean isExistGroup(String name) {
        return groupRepository.findByName(name).isPresent();
    }

    public boolean isExistGroup(Long id) {
        return groupRepository.findById(id).isPresent();
    }
}