package com.nexters.wiw.api.service;

import java.util.List;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.UserNotExistedException;
import com.nexters.wiw.api.ui.UserRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public User getOne(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotExistedException(ErrorType.NOT_FOUND, "NOT_FOUND"));
    }

    public List<User> getList(String email, String nickname) {
        return userRepository.findByEmailOrNickname(email, nickname);
    }

    @Transactional
    public User save(final UserRequestDto userRequestDto) {
        User user = userRequestDto.toEntity(bCryptPasswordEncoder);
        // try {
     authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userRequestDto.getEmail(), userRequestDto.getPassword()));
            // } catch (DisabledException e) {
            // throw new Exception("USER_DISABLED", e);
            // } catch (BadCredentialsException e) {
            // throw new Exception("INVALID_CREDENTIALS", e);
            // }
            

        return userRepository.save(user);
    }

    @Transactional
    public User patch(final Long id, final User user) {
        return getOne(id).update(user);
    }

    @Transactional
    public void delete(final Long id) {
        userRepository.deleteById(id);
    }
}
