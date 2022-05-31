package com.example.marketboro.controller;

import com.example.marketboro.dto.request.UserRequestDto.JoinRequestDto;
import com.example.marketboro.dto.request.UserRequestDto.LoginRequestDto;
import com.example.marketboro.dto.response.UserResponseDto.LoginResponseDto;
import com.example.marketboro.entity.User;
import com.example.marketboro.entity.UserRoleEnum;
import com.example.marketboro.service.KakaoService;
import com.example.marketboro.service.RedisService;
import com.example.marketboro.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @Mock
    KakaoService kakaoService;

    @Mock
    RedisService redisService;

    MockMvc mockMvc;

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    @DisplayName("회원가입")
    public void joinUser() throws Exception {
        // given
        JoinRequestDto requestDto = new JoinRequestDto(
                "sseioul@naver.com",
                "1234",
                "1234",
                "김기윤",
                "key"
        );

        User user = User.builder()
                .username("sseioul@naver.com")
                .password("1234")
                .name("김기윤")
                .nickname("key")
                .role(UserRoleEnum.USER)
                .build();

        when(userService.join(any(JoinRequestDto.class))).thenReturn(user);

        ObjectMapper objectMapper = new ObjectMapper();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value("success"))
                .andExpect(jsonPath("msg").value("회원가입"))
                .andDo(print());

    }

    @Test
    @DisplayName("로그인")
    public void loginUser() throws Exception {
        // given
        LoginRequestDto requestDto = new LoginRequestDto("sseioul@naver.com", "1234");
        LoginResponseDto responseDto = LoginResponseDto.builder()
                .username("sseioul@naver.com")
                .name("김기윤")
                .nickname("key")
                .accessToken("accessToken")
                .refreshToken("refreshToken")
                .build();

        when(userService.login(any(LoginRequestDto.class))).thenReturn(responseDto);

        ObjectMapper objectMapper = new ObjectMapper();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto))
        );

        // then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("result").value("success"))
                .andExpect(jsonPath("msg").value("로그인"))
                .andDo(print());
    }
}
