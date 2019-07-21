package com.nexters.wiw.api.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void selectTest() {
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(0);
    }

    @Test
    public void selectContainsKeywordTest() {
        List<User> users = userRepository.findByNameContains("name");
        assertThat(users.size()).isEqualTo(0);
    }

}