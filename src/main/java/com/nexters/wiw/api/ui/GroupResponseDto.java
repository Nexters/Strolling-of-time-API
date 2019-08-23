package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.common.ModelMapperUtil;
import com.nexters.wiw.api.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GroupResponseDto {

    private Long id;
    private String category;
    private String name;
    private String description;
    private String profileImage;
    private String backgroundImage;
    private LocalDateTime created;
    private int memberLimit;
    private boolean active;

    static public GroupResponseDto of(Group group) {
        ModelMapper mapper = ModelMapperUtil.getModelMapper();
        GroupResponseDto instance = mapper.map(group, GroupResponseDto.class);

        return instance;
    }

    static public List<GroupResponseDto> ofList(List<Group> groups) {
        List<GroupResponseDto> instances = groups.stream()
                                                 .map(GroupResponseDto :: of)
                                                 .collect(Collectors.toList());
        return instances;
    }
}
