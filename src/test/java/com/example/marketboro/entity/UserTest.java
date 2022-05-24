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

public class UserTest {

    @Autowired
    EntityManager em;

    private String username;
    private String password;
    private String passwordCheck;
    private String name;
    private String nickname;

    @BeforeEach
    public void setUp() {
        username = "username@username.com";
        password = "password";
        passwordCheck = "password";
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
                passwordCheck,
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

        //then
        assertEquals(username, user.getUsername());
        assertEquals(password, user.getPassword());
        assertEquals(name, user.getName());
        assertEquals(nickname, user.getNickname());
        assertEquals(UserRoleEnum.USER, user.getRole());
    }

}
