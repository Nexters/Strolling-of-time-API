package com.nexters.wiw.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.nexters.wiw.api.domain.*;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.*;
import com.nexters.wiw.api.ui.GroupRequestDto;

import com.nexters.wiw.api.ui.GroupResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupMemeberService groupMemeberService;

    @Autowired
    private AuthService authService;

    
    @Transactional
    public Group save(String authHeader, GroupRequestDto groupRequestDto) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        Group group = GroupRequestDto.to(groupRequestDto);

        groupRepository.save(group);
        groupMemeberService.joinGroup(authHeader, authService.findIdByToken(authHeader), group.getId());

        return group;
    }

    @Transactional
    public Group update(String authHeader, Long id, GroupRequestDto groupRequestDto) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        Group origin = groupRepository.getOne(id);
        Group updated = origin.update(GroupRequestDto.to(groupRequestDto));

        return updated;
    }

    @Transactional
    public void delete(String authHeader, Long id) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        groupRepository.deleteById(id);
    }

    public Group getGroupById(String authHeader, Long id) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        return groupRepository.findById(id).orElseThrow(GroupNotFoundException::new);
    }

    public List<Group> getGroupByUserId(String authHeader, Long id) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        List<Group> groups = new ArrayList<>();
        User user = userRepository.getOne(id);

        for(GroupMember member : user.getMembers()){
            groups.add(member.getGroup());
        }

        return groups;
    }

    public Page<GroupResponseDto> getGroupByFilter(String authHeader, Pageable pageable, Map<String, Object> filter) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        if(filter.size() == 0) return groupRepository.findAll(pageable).map(GroupResponseDto :: of);
        else return groupRepository.findAll(GroupSpecification.search(filter), pageable).map(GroupResponseDto :: of);
    }
}
