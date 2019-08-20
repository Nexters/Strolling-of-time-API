package com.nexters.wiw.api.ui;

import com.nexters.wiw.api.common.ModelMapperUtil;
import com.nexters.wiw.api.domain.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
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
        System.out.println(group.isActive());
        System.out.println(group.getName());
        System.out.println(group.getBackgroundImage());
        System.out.println(group.getCategory());
        System.out.println(group.getProfileImage());

        GroupResponseDto instance = mapper.map(group, GroupResponseDto.class);

        System.out.println(instance.id);
        System.out.println(instance.backgroundImage);
        System.out.println(instance.active);
        System.out.println(instance.category);
        System.out.println(instance.profileImage);
        return instance;
    }

    static public List<GroupResponseDto> ofList(List<Group> groups) {
        List<GroupResponseDto> instances = groups.stream()
                                                 .map(GroupResponseDto :: of)
                                                 .collect(Collectors.toList());
        return instances;
    }
}
