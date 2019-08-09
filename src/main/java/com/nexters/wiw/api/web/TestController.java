package com.nexters.wiw.api.web;

import com.nexters.wiw.api.ui.UserResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/api/v1/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@Slf4j
public class TestController {
    @GetMapping
    public ResponseEntity<UserResponseDto> select(PageRequest pageRequest) {
        log.info("시계는 와치 와치");
        return ResponseEntity.ok(new UserResponseDto());
    }
}
