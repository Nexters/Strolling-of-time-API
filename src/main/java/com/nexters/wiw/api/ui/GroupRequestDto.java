package com.nexters.wiw.api.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.nexters.wiw.api.common.ModelMapperUtil;
import com.nexters.wiw.api.domain.*;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.modelmapper.ModelMapper;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupRequestDto {

    @Column(nullable = false)
    private String category;

    @Column(length = 45, nullable = false, unique = true)
    private String name;

    @Column(length = 100)
    private String description;

    @Column(name = "profile_image")
    @ColumnDefault(value = "'default_group_profile.png'")
    @Pattern(regexp = ".*\\.jpg|.*\\.JPG|.*\\.png|.*\\.PNG|.*\\.gif|.*\\.GIF", message = "jpg, png, gif 확장자의 이미지만 지원합니다.")
    private String profileImage;

    @Column(name = "background_image")
    @ColumnDefault(value = "'default_group_background.png'")
    @Pattern(regexp = ".*\\.jpg|.*\\.JPG|.*\\.png|.*\\.PNG|.*\\.gif|.*\\.GIF", message = "jpg, png, gif 확장자의 이미지만 지원합니다.")
    private String backgroundImage;

    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @CreatedDate
    private LocalDateTime created;

    @Column(name = "member_limit")
    @Min(2) @Max(10)
    @ColumnDefault(value = "6")
    private int memberLimit = 6;

    @ColumnDefault(value = "true")
    private boolean active= true;

    public GroupRequestDto(Group group) {
    }

    public static Group to(GroupRequestDto dto) {
        ModelMapper mapper = ModelMapperUtil.getModelMapper();
        Group instance = mapper.map(dto, Group.class);

        instance.setMembers(new ArrayList<>());
        instance.setMissions(new ArrayList<>());
        instance.setNotices(new ArrayList<>());

        return instance;
    }

    public static List<Group> toList(List<GroupRequestDto> dtos) {
        List<Group> instances = dtos.stream()
                                 .map(GroupRequestDto :: to)
                                 .collect(Collectors.toList());

        return instances;
    }
}
