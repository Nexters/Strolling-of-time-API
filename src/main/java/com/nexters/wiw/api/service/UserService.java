package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.ui.UserRequestDto;
import com.nexters.wiw.api.ui.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponseDto save(UserRequestDto userRequestDto) {
        User savedUser = userRepository.save(userRequestDto.of());
        return new UserResponseDto();
    }

}
