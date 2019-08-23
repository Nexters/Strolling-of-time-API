package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.*;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.GroupNoticeNotFoundException;
import com.nexters.wiw.api.exception.UnAuthorizedException;
import com.nexters.wiw.api.ui.GroupNoticeRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GroupNoticeService {

    @Autowired
    GroupNoticeRepository groupNoticeRepository;
    @Autowired
    GroupRepository groupRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthService authService;

    @Transactional
    public GroupNotice save(String authHeader, Long id, GroupNoticeRequestDto groupNoticeRequestDto) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        User user = userRepository.findById(authService.findIdByToken(authHeader)).get();
        Group group = groupRepository.getOne(id);
        GroupNotice groupNotice = groupNoticeRequestDto.toEntity();
        groupNotice.setGroup(group);
        groupNotice.setUser(user);

        groupNoticeRepository.save(groupNotice);

        return groupNotice;
    }

    @Transactional
    public GroupNotice update(Long id, GroupNoticeRequestDto groupNoticeRequestDto) {
        GroupNotice origin = getGroupNoticeById(id);
        GroupNotice updated = origin.update(groupNoticeRequestDto.toEntity());

        return updated;
    }

    @Transactional
    public void delete(Long id) { groupNoticeRepository.deleteById(id); }

    public List<GroupNotice> getGroupNoticeByTitle(String keyword) {
        return groupNoticeRepository.findByTitleContaining(keyword).orElseThrow(GroupNoticeNotFoundException :: new);
    }

    public List<GroupNotice> getGroupByGroupId(Long groupId) {
        Group group = groupRepository.getOne(groupId);
        return groupNoticeRepository.findByGroup(group).orElseThrow(GroupNoticeNotFoundException :: new);
    }

    public List<GroupNotice> getGroupByUserId(Long userId) {
        User user = userRepository.findById(userId).get();
        return groupNoticeRepository.findByUser(user).orElseThrow(GroupNoticeNotFoundException :: new);
    }

    public GroupNotice getGroupNoticeById(Long id) {
        return groupNoticeRepository.findById(id).orElseThrow(GroupNoticeNotFoundException :: new);
    }
}
