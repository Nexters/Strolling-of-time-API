package com.nexters.wiw.api.service;

import java.util.List;

import com.nexters.wiw.api.domain.*;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.*;
import com.nexters.wiw.api.ui.GroupRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private AuthService authService;

    
    @Transactional
    public Group save(String authHeader, GroupRequestDto groupRequestDto) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        User user = userRepository.getOne(authService.findIdByToken(authHeader));

        GroupMember member = new GroupMember();
        Group group = groupRequestDto.toEntity();
        groupRepository.save(group);
        member.setUserId(user.getId());
//        member.setUser(user);
        member.setGroupId(group.getId());
//        member.setGroup(group);
        group.addGroupMember(member);
        groupMemberRepository.save(member);
        return group;
    }

    @Transactional
    public Group update(String authHeader, Long id, GroupRequestDto groupRequestDto) {
        Group origin = getGroupById(authHeader, id);
        Group updated = origin.update(groupRequestDto.toEntity());

        return updated;
    }

    @Transactional
    public void delete(String authHeader, Long id) {
        groupRepository.deleteById(id);
    }

    public List<Group> getAllgroup(String authHeader) {
        return groupRepository.findAll();
    }

    public Group getGroupById(String authHeader, Long id) {
        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

    public List<Group> getGroupByName(String authHeader, String keyword) {
        return groupRepository.findByNameContaining(keyword).orElseThrow(GroupNotFoundException::new);
    }

}