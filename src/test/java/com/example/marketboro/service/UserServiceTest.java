package com.example.marketboro.service;

import com.example.marketboro.dto.request.UserRequestDto;
import com.example.marketboro.dto.request.UserRequestDto.JoinRequestDto;
import com.example.marketboro.entity.User;
import com.example.marketboro.entity.UserRoleEnum;
import com.example.marketboro.repository.UserRepository;
import com.example.marketboro.repository.cart.CartRepository;
import com.example.marketboro.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserRepository userRepository;

    @Mock
    JwtTokenProvider jwtTokenProvider;

    @Mock
    CartRepository cartRepository;

    @Mock
    RedisService redisService;

    @Test
    @DisplayName("회원가입")
    public void joinUser() {

        JoinRequestDto requestDto = new JoinRequestDto(
                "sseioul@naver.com",
                "1234",
                "1234",
                "김기윤",
                "key");

        User user = User.builder()
                .username("test@test.com")
                .password("1234")
                .name("test")
                .nickname("test")
                .role(UserRoleEnum.USER)
                .build();

        UserService userService = new UserService(
                passwordEncoder,
                userRepository,
                jwtTokenProvider,
                cartRepository,
                redisService);
        when(userRepository.findByUsername(any()))
                .thenReturn(Optional.of(user));
        when(userRepository.findByNickname(any()))
                .thenReturn(Optional.of(user));

    }

}
