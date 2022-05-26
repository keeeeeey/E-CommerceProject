package com.example.marketboro.service;

import com.example.marketboro.dto.request.UserRequestDto;
import com.example.marketboro.dto.request.UserRequestDto.JoinRequestDto;
import com.example.marketboro.dto.request.UserRequestDto.LoginRequestDto;
import com.example.marketboro.dto.response.UserResponseDto;
import com.example.marketboro.dto.response.UserResponseDto.LoginResponseDto;
import com.example.marketboro.entity.User;
import com.example.marketboro.entity.UserRoleEnum;
import com.example.marketboro.exception.ErrorCustomException;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName("회원가입 정상케이스")
    public void joinUser() {
        // given
        JoinRequestDto requestDto = new JoinRequestDto(
                "sseioul@naver.com",
                "1234",
                "1234",
                "김기윤",
                "key");

        UserService userService = new UserService(
                passwordEncoder,
                userRepository,
                jwtTokenProvider,
                cartRepository,
                redisService);

        // when
        User joinUser = userService.join(requestDto);

        // then
        assertEquals(joinUser.getUsername(), requestDto.getUsername());

    }

    @Test
    @DisplayName("회원가입 아이디 중복")
    public void joinUserFailedUsername() {
        // given
        JoinRequestDto requestDto = new JoinRequestDto(
                "sseioul@naver.com",
                "1234",
                "1234",
                "김기윤",
                "key");

        User user = User.builder()
                .username("sseioul@naver.com")
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

        // when
        Exception exception = assertThrows(ErrorCustomException.class, () -> {
            userService.join(requestDto);
        });

        // then
        assertEquals("이미 사용중인 아이디입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("회원가입 닉네임 중복")
    public void joinUserFailedNickname() {
        // given
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
                .nickname("key")
                .role(UserRoleEnum.USER)
                .build();

        UserService userService = new UserService(
                passwordEncoder,
                userRepository,
                jwtTokenProvider,
                cartRepository,
                redisService);
        when(userRepository.findByNickname(any()))
                .thenReturn(Optional.of(user));

        // when
        Exception exception = assertThrows(ErrorCustomException.class, () -> {
            userService.join(requestDto);
        });

        // then
        assertEquals("이미 사용중인 닉네임입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("회원가입 비밀번호 불일치")
    public void joinUserFailedPassword() {
        // given
        JoinRequestDto requestDto = new JoinRequestDto(
                "sseioul@naver.com",
                "1234",
                "12345678",
                "김기윤",
                "key");

        UserService userService = new UserService(
                passwordEncoder,
                userRepository,
                jwtTokenProvider,
                cartRepository,
                redisService);

        // when
        Exception exception = assertThrows(ErrorCustomException.class, () -> {
            userService.join(requestDto);
        });

        // then
        assertEquals("비밀번호가 일치하지 않습니다.", exception.getMessage());
    }

//    @Test
//    @DisplayName("로그인 정상케이스")
//    public void loginUser() {
//        // given
//        LoginRequestDto requestDto = new LoginRequestDto("sseioul@naver.com", "1234");
//
//        User user = User.builder()
//                .username("sseioul@naver.com")
//                .password("1234")
//                .name("김기윤")
//                .nickname("key")
//                .role(UserRoleEnum.USER)
//                .build();
//
//        UserService userService = new UserService(
//                passwordEncoder,
//                userRepository,
//                jwtTokenProvider,
//                cartRepository,
//                redisService);
//        when(userRepository.findByUsername(any()))
//                .thenReturn(Optional.of(user));
//
//        // when
//        LoginResponseDto responseDto = userService.login(requestDto);
//
//        // then
//        assertEquals(user.getUsername(), responseDto.getUsername());
//
//    }

}