package com.example.marketboro.entity;

import com.example.marketboro.dto.request.UserRequestDto;
import com.example.marketboro.dto.request.UserRequestDto.JoinRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest {

    @Test
    @DisplayName("정상 케이스")
    public void createOrder() {

        // given
        User user = user();

        //when
        Order order = Order.builder()
                .user(user)
                .build();

        //then
        assertEquals(user.getUsername(), order.getUser().getUsername());
        assertEquals(user.getPassword(), order.getUser().getPassword());
        assertEquals(user.getName(), order.getUser().getName());
        assertEquals(user.getNickname(), order.getUser().getNickname());
        assertEquals(UserRoleEnum.USER, order.getUser().getRole());
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
