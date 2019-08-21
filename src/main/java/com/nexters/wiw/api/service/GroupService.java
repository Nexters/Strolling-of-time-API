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

        User user = userRepository.findById(authService.findIdByToken(authHeader)).get();

        Group group = groupRequestDto.toEntity();
        groupRepository.save(group);

        GroupMember member = new GroupMember(group, user, true);
        group.addGroupMember(member);
        groupMemberRepository.save(member);

        return group;
    }

    @Transactional
    public Group update(String authHeader, Long id, GroupRequestDto groupRequestDto) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        Group origin = getGroupById(authHeader, id);
        Group updated = origin.update(groupRequestDto.toEntity());

        return updated;
    }

    @Transactional
    public void delete(String authHeader, Long id) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");
        groupRepository.deleteById(id);
    }

    public List<Group> getAllgroup(String authHeader) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        return groupRepository.findAll();
    }

    public Group getGroupById(String authHeader, Long id) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

    public List<Group> getGroupByName(String authHeader, String keyword) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        return groupRepository.findByNameContaining(keyword).orElseThrow(GroupNotFoundException::new);
    }

}