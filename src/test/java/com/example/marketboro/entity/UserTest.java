package com.example.marketboro.entity;

import com.example.marketboro.dto.request.UserRequestDto.JoinRequestDto;
import com.example.marketboro.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class UserTest {

    @Autowired
    EntityManager em;

    @Autowired
    UserRepository userRepository;

    private String username;
    private String password;
    private String passwordcheck;
    private String name;
    private String nickname;

    @AfterEach
    public void setDown() {
        userRepository.deleteAll();
    }

    @BeforeEach
    public void setUp() {
        username = "username@username.com";
        password = "password";
        passwordcheck = "password";
        name = "name";
        nickname = "nickname";
    }

    @Test
    @DisplayName("정상 케이스")
    public void joinUser() {

        // given
        JoinRequestDto requestDto = new JoinRequestDto(
                username,
                password,
                passwordcheck,
                name,
                nickname
        );

        //when
        User user = User.builder()
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .name(requestDto.getName())
                .nickname(requestDto.getNickname())
                .role(UserRoleEnum.USER)
                .build();

        User save = userRepository.save(user);

        //then
        assertEquals(username, save.getUsername());
        assertEquals(password, save.getPassword());
        assertEquals(name, save.getName());
        assertEquals(nickname, save.getNickname());
        assertEquals(UserRoleEnum.USER, save.getRole());
    }

}
