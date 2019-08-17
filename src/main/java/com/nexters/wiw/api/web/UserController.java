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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserByUserId(@RequestHeader("Authorization") String authHeader, @PathVariable("id") final Long id) {
        User user = userService.getOne(authHeader, id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<User>> getUserList(@RequestHeader("Authorization") String authHeader, @RequestParam(value="email", required=false) final String email,
            @RequestParam(value="nickname", required=false) String nickname) {
        List<User> userList = userService.getList(authHeader, email, nickname);
        return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<User> signUpUser(@RequestBody final UserRequestDto userRequestDto) {
        User user = userService.save(userRequestDto);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@RequestHeader("Authorization") String authHeader, @PathVariable("id") final Long id, @RequestBody final User user) {
        final User patchedUser = userService.patch(authHeader, id, user);
        return new ResponseEntity<User>(patchedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") String authHeader, @PathVariable("id") final Long id) {
        userService.delete(authHeader, id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
