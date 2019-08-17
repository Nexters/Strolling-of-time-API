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
    private AuthService authService;

    
    @Transactional
    public Group save(String authHeader, GroupRequestDto groupRequestDto) {
        isValidToken(authHeader);


        GroupMember member = new GroupMember(user);
        Group group = groupRequestDto.toEntity();
        member.setGroup(group);
        group.addGroupMember(member);

        groupRepository.save(group);

        return group;
    }

    @Transactional
    public Group update(String authHeader, Long id, GroupRequestDto groupRequestDto) {
        String email = authService.decodeToken(authHeader);
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException :: new);

        if(authService.isValidateToken(authHeader, user)) {
            throw new NotValidTokenException(ErrorType.UNAUTHORIZED, "잘못된 접근입니다.");
        }

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

    public void isValidToken(String token) {
        if(authService.isValidateToken(token)) {
            throw new NotValidTokenException(ErrorType.UNAUTHORIZED, "잘못된 접근입니다.");
        }
    }

}