package com.example.marketboro.repository;

import com.example.marketboro.entity.User;
import com.example.marketboro.entity.UserRoleEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    
    @Test
    @DisplayName("saveUser")
    public void saveUser() {
        // given
        User user = user();

        // when
        User savedUser = userRepository.save(user);

        // then
        assertEquals(user.getUsername(), savedUser.getUsername());
        assertEquals(user.getName(), savedUser.getName());
        assertEquals(user.getNickname(), savedUser.getNickname());
        assertEquals(user.getRole(), savedUser.getRole());
    }

    @Test
    @DisplayName("findByUsername")
    public void findByUsernameTest() {
        // given
        User user = user();
        userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByUsername(user.getUsername());

        // then
        assertEquals(user.getUsername(), findUser.get().getUsername());
        assertEquals(user.getName(), findUser.get().getName());
        assertEquals(user.getNickname(), findUser.get().getNickname());
        assertEquals(user.getRole(), findUser.get().getRole());
    }

    @Test
    @DisplayName("findByNickname")
    public void findByNicknameTest() {
        // given
        User user = user();
        userRepository.save(user);

        // when
        Optional<User> findUser = userRepository.findByNickname(user.getNickname());

        // then
        assertEquals(user.getUsername(), findUser.get().getUsername());
        assertEquals(user.getName(), findUser.get().getName());
        assertEquals(user.getNickname(), findUser.get().getNickname());
        assertEquals(user.getRole(), findUser.get().getRole());
    }

    private User user() {
        return User.builder()
                .userId(1L)
                .username("sseioul@naver.com")
                .password("1234")
                .name("김기윤")
                .nickname("key")
                .role(UserRoleEnum.USER)
                .build();
    }
}
