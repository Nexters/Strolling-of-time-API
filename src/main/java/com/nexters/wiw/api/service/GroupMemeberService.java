package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.*;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.GroupMemberNotFoundException;
import com.nexters.wiw.api.exception.NotFoundException;
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

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND, "아이디에 해당하는 유저가 존재하지 않습니다."));
        Group group = groupRepository.getOne(groupId);

        GroupMember groupMember = new GroupMember(group, user, false);
        groupMemberRepository.save(groupMember);

        return group;
    }

    public void leaveGroup(String authHeader, Long userId, Long groupId) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND, "아이디에 해당하는 유저가 존재하지 않습니다."));
        Group group = groupRepository.getOne(groupId);

        GroupMember groupMember = groupMemberRepository.findByGroupAndUser(group, user)
                                                       .orElseThrow(GroupMemberNotFoundException::new);

        groupMemberRepository.delete(groupMember);
    }
}
