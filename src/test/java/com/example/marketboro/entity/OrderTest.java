package com.example.marketboro.entity;

import com.example.marketboro.dto.request.UserRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

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
    public void createOrder() {

        // given
        UserRequestDto.JoinRequestDto requestDto = new UserRequestDto.JoinRequestDto(
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

        Order order = Order.builder()
                .user(user)
                .build();

        //then
        assertEquals(username, order.getUser().getUsername());
        assertEquals(password, order.getUser().getPassword());
        assertEquals(name, order.getUser().getName());
        assertEquals(nickname, order.getUser().getNickname());
        assertEquals(UserRoleEnum.USER, order.getUser().getRole());
    }
}
