package com.nexters.wiw.api.service;

import java.util.List;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.BadRequestException;
import com.nexters.wiw.api.exception.NotFoundException;
import com.nexters.wiw.api.exception.UnAuthorizedException;
import com.nexters.wiw.api.ui.UserPatchRequestDto;
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
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND, "아이디에 해당하는 유저가 존재하지 않습니다."));
    }

    public List<User> getList(String authHeader, String query) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

        // TODO 모든 유저는 과부화가 심하니 예외처리로 변경하는 건 어떤지..
        if(query == null) 
            return userRepository.findAll();

        return userRepository.findByEmailContainingOrNicknameContaining(query, query);
    }

    @Transactional
    public User save(final UserRequestDto userRequestDto) {
        User user = userRequestDto.toEntity(bCryptPasswordEncoder);
        return userRepository.save(user);
    }

    @Transactional
    public User patch(String authHeader, final Long id, final UserPatchRequestDto userPatchRequestDto) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

        return getOne(authHeader, id).update(userPatchRequestDto);
    }

    @Transactional
    public void delete(String authHeader, final Long id) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

        userRepository.deleteById(id);
    }
}
