package com.nexters.wiw.api.service;

import javax.annotation.Resource;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.exception.UserDuplicatedException;
import com.nexters.wiw.api.exception.UserNotExistedException;
import com.nexters.wiw.api.ui.UserRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Resource
    private PasswordEncoder bCryptPasswordEncoder;

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotExistedException::new);
    }

    @Transactional
    public void save(final UserRequestDto userRequestDto) {
        if(isExistUser(userRequestDto.getEmail()))
            throw new UserDuplicatedException();

        String encryptedPassword = bCryptPasswordEncoder.encode(userRequestDto.getPassword());
        userRequestDto.setPassword(encryptedPassword);
        userRepository.save(userRequestDto.toEntity());
    }

    @Transactional
    public User patch(final Long id, final User user) {
        return getUserById(id).update(user);
    }

    public boolean isExistUser(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

}
