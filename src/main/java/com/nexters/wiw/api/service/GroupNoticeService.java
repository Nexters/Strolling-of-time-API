package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.*;
import com.nexters.wiw.api.exception.GroupNoticeNotFoundException;
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

    @Transactional
    public GroupNotice save(GroupNoticeRequestDto groupNoticeRequestDto) {
        GroupNotice groupNotice = groupNoticeRequestDto.toEntity();
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

//    public List<GroupNotice> getGroupByUserId(Long userId) {
//        User user = userRepository.getOne(userId);
//        return groupNoticeRepository.findByUser(user).orElseThrow(GroupNoticeNotFoundException :: new);
//    }

    public GroupNotice getGroupNoticeById(Long id) {
        return groupNoticeRepository.findById(id).orElseThrow(GroupNoticeNotFoundException :: new);
    }
}
