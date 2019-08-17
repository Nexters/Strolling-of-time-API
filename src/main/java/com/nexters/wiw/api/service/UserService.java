package com.nexters.wiw.api.service;

import java.util.List;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.UnAuthorizedException;
import com.nexters.wiw.api.exception.UserNotExistedException;
import com.nexters.wiw.api.ui.UserRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public User getOne(String authHeader, Long id) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotExistedException(ErrorType.NOT_FOUND, "NOT_FOUND"));
    }

    public List<User> getList(String authHeader, String email, String nickname) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        return userRepository.findByEmailOrNickname(email, nickname);
    }

    @Transactional
    public User save(final UserRequestDto userRequestDto) {
        User user = userRequestDto.toEntity(bCryptPasswordEncoder);
        return userRepository.save(user);
    }

    @Transactional
    public User patch(String authHeader, final Long id, final User user) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        return getOne(authHeader, id).update(user);
    }

    @Transactional
    public void delete(String authHeader, final Long id) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "UNAUTHORIZED");

        userRepository.deleteById(id);
    }
}
