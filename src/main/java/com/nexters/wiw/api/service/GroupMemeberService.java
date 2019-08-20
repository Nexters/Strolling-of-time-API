package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.*;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.GroupMemberNotFoundException;
import com.nexters.wiw.api.exception.UnAuthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GroupMemeberService {

    @Autowired
    GroupMemberRepository groupMemberRepository;

    @Autowired
    AuthService authService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @Transactional
    public Group joinGroup(String authHeader, Long userId, Long groupId) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        User user = userRepository.getOne(userId);
        Group group = groupRepository.getOne(groupId);

        GroupMember groupMember = new GroupMember(group, user, false);
        groupMemberRepository.save(groupMember);

        return group;
    }

    public void quitGroup(String authHeader, Long userId, Long groupId) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        User user = userRepository.getOne(userId);
        Group group = groupRepository.getOne(groupId);

        GroupMember groupMember = groupMemberRepository.findByGroupAndUser(group, user)
                                                       .orElseThrow(GroupMemberNotFoundException::new);

        groupMemberRepository.delete(groupMember);
    }
}
