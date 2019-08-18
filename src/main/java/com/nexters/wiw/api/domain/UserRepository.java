package com.nexters.wiw.api.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    List<User> findByEmailContaining(String nickname);
    List<User> findByNicknameContaining(String nickname);
    List<User> findByEmailOrNicknameContaining(String email, String nickname);
}
