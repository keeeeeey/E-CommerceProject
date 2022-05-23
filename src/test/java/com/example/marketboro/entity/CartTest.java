package com.example.marketboro.entity;

import com.example.marketboro.dto.request.ProductRequestDto;
import com.example.marketboro.dto.request.UserRequestDto;
import com.example.marketboro.repository.ProductRepository;
import com.example.marketboro.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CartTest {

    @Autowired
    EntityManager em;

    private String username;
    private String password;
    private String passwordcheck;
    private String name;
    private String nickname;

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
    public void createCart() {

        // given
        UserRequestDto.JoinRequestDto requestDto = new UserRequestDto.JoinRequestDto(
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

        Cart cart = Cart.builder()
                .user(user)
                .build();

        //then
        assertEquals(username, cart.getUser().getUsername());
        assertEquals(password, cart.getUser().getPassword());
        assertEquals(name, cart.getUser().getName());
        assertEquals(nickname, cart.getUser().getNickname());
        assertEquals(UserRoleEnum.USER, cart.getUser().getRole());
    }
}
