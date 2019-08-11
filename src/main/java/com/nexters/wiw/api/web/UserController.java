package com.nexters.wiw.api.web;

import javax.validation.Valid;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.service.UserService;
import com.nexters.wiw.api.ui.UserRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<User> signUpUser(@RequestBody @Valid final UserRequestDto userRequestDto) {
        User user = null;
        user = userService.save(userRequestDto);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable("id") final Long id,
            @RequestBody final User user) {
        final User patchedUser = userService.patch(id, user);
        return new ResponseEntity<User>(patchedUser, HttpStatus.OK);
    }
}
