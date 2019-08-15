package com.nexters.wiw.api.web;

import java.util.List;

import javax.validation.Valid;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.service.UserService;
import com.nexters.wiw.api.ui.UserRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByUserId(@PathVariable("id") final Long id) {
        User user = userService.getOne(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUserList(@RequestParam("email") final String email,
            @RequestParam("nickname") String nickname) {
        List<User> userList = userService.getList(email, nickname);
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> signUpUser(@RequestBody @Valid final UserRequestDto userRequestDto) {
        User user = userService.save(userRequestDto);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable("id") final Long id, @RequestBody final User user) {
        final User patchedUser = userService.patch(id, user);
        return new ResponseEntity<User>(patchedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") final Long id) {
        userService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
