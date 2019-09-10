package com.nexters.wiw.api.service;

import java.util.List;

import javax.transaction.Transactional;

import com.nexters.wiw.api.domain.Group;
import com.nexters.wiw.api.domain.GroupNotice;
import com.nexters.wiw.api.domain.GroupNoticeRepository;
import com.nexters.wiw.api.domain.GroupRepository;
import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.exception.GroupNoticeNotFoundException;
import com.nexters.wiw.api.exception.UserNotFoundException;
import com.nexters.wiw.api.ui.NoticeRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public GroupNotice save(Long userId, NoticeRequestDto groupNoticeRequestDto) {

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Group group = groupRepository.getOne(groupNoticeRequestDto.getGroupId());
        GroupNotice groupNotice = groupNoticeRequestDto.toEntity();
        groupNotice.setGroup(group);
        groupNotice.setUser(user);

        groupNoticeRepository.save(groupNotice);

        return groupNotice;
    }

    @Transactional
    public GroupNotice update(Long id, NoticeRequestDto groupNoticeRequestDto) {
        GroupNotice origin = getGroupNoticeById(id);
        GroupNotice updated = origin.update(groupNoticeRequestDto.toEntity());

        return updated;
    }

    @Transactional
    public void delete(Long id) {
        groupNoticeRepository.deleteById(id);
    }

    public List<GroupNotice> getGroupNoticeByTitle(String keyword) {
        return groupNoticeRepository.findByTitleContaining(keyword).orElseThrow(GroupNoticeNotFoundException::new);
    }

    public List<GroupNotice> getGroupByGroupId(Long groupId) {
        Group group = groupRepository.getOne(groupId);
        return groupNoticeRepository.findByGroup(group).orElseThrow(GroupNoticeNotFoundException::new);
    }

    public List<GroupNotice> getGroupByUserId(Long userId) {
        User user = userRepository.findById(userId).get();
        return groupNoticeRepository.findByUser(user).orElseThrow(GroupNoticeNotFoundException::new);
    }

    public GroupNotice getGroupNoticeById(Long id) {
        return groupNoticeRepository.findById(id).orElseThrow(GroupNoticeNotFoundException::new);
    }
}
