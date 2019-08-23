package com.nexters.wiw.api.web;

import javax.validation.Valid;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.service.UserService;
import com.nexters.wiw.api.ui.UserPageListDto;
import com.nexters.wiw.api.ui.UserPatchRequestDto;
import com.nexters.wiw.api.ui.UserRequestDto;
import com.nexters.wiw.api.ui.UserResponseDto;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.PagedResources.PageMetadata;
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

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")

    public ResponseEntity<UserResponseDto> getUserByUserId(@RequestHeader("Authorization") final String authHeader,
            @PathVariable("id") final Long id) {
        User user = userService.getOne(authHeader, id);
        UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);
        return new ResponseEntity<UserResponseDto>(userDto, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<UserPageListDto> getUserList(@RequestHeader("Authorization") final String authHeader,
            @PageableDefault final Pageable pageable,
            @RequestParam(value = "query", required = false) final String query) {
        Page<User> userList = userService.getList(authHeader, pageable, query);
        Page<UserResponseDto> userDtoList = userList.map(user -> modelMapper.map(user, UserResponseDto.class));


        PageMetadata pageMetadata = new PageMetadata(userDtoList.getSize(), userDtoList.getTotalElements(),
                userDtoList.getTotalPages());
                
        UserPageListDto userPageListDto = new UserPageListDto(userDtoList.getContent(), pageMetadata);
        return new ResponseEntity<UserPageListDto>(userPageListDto, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<UserResponseDto> signUpUser(@RequestBody @Valid final UserRequestDto userRequestDto) {
        User user = userService.save(userRequestDto);
        UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);
        return new ResponseEntity<UserResponseDto>(userDto, HttpStatus.CREATED);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserResponseDto> patchUser(@RequestHeader("Authorization") final String authHeader,
            @PathVariable("id") final Long id, @RequestBody @Valid final UserPatchRequestDto userPatchRequestDto) {
        User user = userService.patch(authHeader, id, userPatchRequestDto);
        UserResponseDto userDto = modelMapper.map(user, UserResponseDto.class);
        return new ResponseEntity<UserResponseDto>(userDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@RequestHeader("Authorization") final String authHeader,
            @PathVariable("id") final Long id) {
        userService.delete(authHeader, id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
