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

    @Test
    @DisplayName("정상 케이스")
    public void createCart() {

        // given
        User user = user();

        //when
        Cart cart = Cart.builder()
                .user(user)
                .build();

        //then
        assertEquals(user.getUsername(), cart.getUser().getUsername());
        assertEquals(user.getPassword(), cart.getUser().getPassword());
        assertEquals(user.getName(), cart.getUser().getName());
        assertEquals(user.getNickname(), cart.getUser().getNickname());
        assertEquals(UserRoleEnum.USER, cart.getUser().getRole());
    }

    private User user() {
        return User.builder()
                .username("sseioul@naver.com")
                .password("1234")
                .name("김기윤")
                .nickname("key")
                .role(UserRoleEnum.USER)
                .build();
    }
}
