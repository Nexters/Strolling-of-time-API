package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.MissionHistoryRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MissionHistoryService {

    private MissionHistoryRepository missionHistoryRepository;
}
