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

    @Test
    @DisplayName("정상 케이스")
    public void joinUser() {

        // given
        JoinRequestDto requestDto = joinRequestDto();

        //when
        User user = user();

        //then
        assertEquals(requestDto.getUsername(), user.getUsername());
        assertEquals(requestDto.getPassword(), user.getPassword());
        assertEquals(requestDto.getName(), user.getName());
        assertEquals(requestDto.getNickname(), user.getNickname());
        assertEquals(UserRoleEnum.USER, user.getRole());
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

    private JoinRequestDto joinRequestDto() {
        return new JoinRequestDto(
                "sseioul@naver.com",
                "1234",
                "1234",
                "김기윤",
                "key"
        );
    }

}
