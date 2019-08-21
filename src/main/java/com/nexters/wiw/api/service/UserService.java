package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.NotFoundException;
import com.nexters.wiw.api.exception.UnAuthorizedException;
import com.nexters.wiw.api.ui.UserPatchRequestDto;
import com.nexters.wiw.api.ui.UserRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public User getOne(final String authHeader, Long id) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorType.NOT_FOUND, "아이디에 해당하는 유저가 존재하지 않습니다."));
    }

    public Page<User> getList(final String authHeader, final Pageable pageable, final String query) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

        // TODO 모든 유저는 과부화가 심하니 예외처리로 변경하는 건 어떤지?
        if(query == null) 
            return userRepository.findAll(pageable);

        // TODO @뒤의 이메일 도메인까지 검색이 되는 것 막기
        return userRepository.findByEmailContainingOrNicknameContaining(query, query, pageable);
    }

    @Transactional
    public User save(final UserRequestDto userRequestDto) {
        User user = userRequestDto.toEntity(bCryptPasswordEncoder);
        return userRepository.save(user);
    }

    @Transactional
    public User patch(final String authHeader, final Long id, final UserPatchRequestDto userPatchRequestDto) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

        return getOne(authHeader, id).update(userPatchRequestDto);
    }

    @Transactional
    public void delete(final String authHeader, final Long id) {
        if (!authService.isValidateToken(authHeader))
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "유효하지 않은 토큰입니다.");

        if(authService.findIdByToken(authHeader) == id)
            throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "계정 삭제는 본인만 가능합니다.");

        userRepository.deleteById(id);
    }
}
