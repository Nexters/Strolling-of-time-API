package com.nexters.wiw.api.web;

import com.nexters.wiw.api.service.UserService;
import com.nexters.wiw.api.ui.UserRequestDto;
import com.nexters.wiw.api.ui.UserResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDto> signUpUser(@RequestBody UserRequestDto userRequestDto) {
        log.info("회원은 와치 와치");
        UserResponseDto userResponseDto = userService.save(userRequestDto);
        return ResponseEntity.created(URI.create("124")).body(userResponseDto);
    }
}
