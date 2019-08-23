package com.nexters.wiw.api.ui;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.PagedResources.PageMetadata;

import java.util.List;

@Getter
@Setter
public class MissionPageListDto {
    private List<MissionResponseDto> missions;
    private PageMetadata pageMetadata;

    public MissionPageListDto(List<MissionResponseDto> missions, PageMetadata pageMetadata) {
        this.missions = missions;
        this.pageMetadata = pageMetadata;
    }
}
