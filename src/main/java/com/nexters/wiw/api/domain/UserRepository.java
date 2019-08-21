package com.nexters.wiw.api.domain;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Page<User> findByEmailContainingOrNicknameContaining(String email, String nickname, Pageable pageable);
}