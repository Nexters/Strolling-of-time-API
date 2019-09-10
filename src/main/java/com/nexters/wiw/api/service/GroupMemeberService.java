package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.domain.GroupMember;
import com.nexters.wiw.api.domain.GroupMemberRepository;
import com.nexters.wiw.api.domain.GroupRepository;
import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.GroupMemberNotFoundException;
import com.nexters.wiw.api.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupMemeberService {
    final GroupMemberRepository groupMemberRepository;
    final AuthService authService;
    final UserRepository userRepository;
    final GroupRepository groupRepository;

    @Transactional
    public Group joinGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorType.NOT_FOUND, "아이디에 해당하는 유저가 존재하지 않습니다."));
        Group group = groupRepository.getOne(groupId);

        GroupMember groupMember = new GroupMember(group, user, false);
        groupMemberRepository.save(groupMember);

        return group;
    }

    public void leaveGroup(Long userId, Long groupId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(ErrorType.NOT_FOUND, "아이디에 해당하는 유저가 존재하지 않습니다."));
        Group group = groupRepository.getOne(groupId);

        GroupMember groupMember = groupMemberRepository.findByGroupAndUser(group, user)
                .orElseThrow(GroupMemberNotFoundException::new);

        groupMemberRepository.delete(groupMember);
    }
}
