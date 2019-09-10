package com.nexters.wiw.api.service;

import com.nexters.wiw.api.domain.User;
import com.nexters.wiw.api.domain.UserRepository;
import com.nexters.wiw.api.domain.error.ErrorType;
import com.nexters.wiw.api.exception.NotFoundException;
import com.nexters.wiw.api.ui.UserPatchRequestDto;
import com.nexters.wiw.api.ui.UserRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    final UserRepository userRepository;
    final PasswordEncoder bCryptPasswordEncoder;

    public User getOne(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ErrorType.NOT_FOUND, "아이디에 해당하는 유저가 존재하지 않습니다."));
    }

    public Page<User> getList(final Pageable pageable, final String query) {
        // TODO 모든 유저는 과부화가 심하니 예외처리로 변경하는 건 어떤지?
        if (query == null)
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
    public User patch(final Long id, final UserPatchRequestDto userPatchRequestDto) {
        return getOne(id).update(userPatchRequestDto);
    }

    @Transactional
    public void delete(final Long id) {
        // TODO flag를 통해 유저를 삭제하는 로직으로 리팩토링
        // if(authService.findIdByToken(authHeader) == id)
        // throw new UnAuthorizedException(ErrorType.UNAUTHORIZED, "계정 삭제는 본인만 가능합니다.");
        userRepository.deleteById(id);
    }
}
