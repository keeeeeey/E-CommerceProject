package com.example.marketboro.controller;

import com.example.marketboro.dto.Success;
import com.example.marketboro.dto.request.UserRequestDto.JoinRequestDto;
import com.example.marketboro.dto.request.UserRequestDto.LoginRequestDto;
import com.example.marketboro.service.KakaoService;
import com.example.marketboro.service.RefreshService;
import com.example.marketboro.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RefreshService refreshService;
    private final KakaoService kakaoService;

    @PostMapping("/user/join")
    public ResponseEntity<Success> join(@Valid @RequestBody JoinRequestDto requestDto) {
        return new ResponseEntity<>(new Success("회원가입",
                userService.join(requestDto)), HttpStatus.OK);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Success> login(@Valid @RequestBody LoginRequestDto requestDto) {
        return new ResponseEntity<>(new Success("로그인",
                userService.login(requestDto)), HttpStatus.OK);
    }

    @PostMapping("/user/refresh")
    public ResponseEntity<Success> refresh(@RequestHeader(value = "AccessAuthorization") String accessToken,
                                           @RequestHeader(value = "RefreshAuthorization") String refreshToken) {
        return new ResponseEntity<>(new Success<>(
                "토큰 재발급 성공", refreshService.refresh(accessToken, refreshToken)), HttpStatus.OK);
    }

    @GetMapping("/user/kakao/callback")
    public ResponseEntity<Success> login(@RequestParam("code") String code) throws JsonProcessingException {
        return new ResponseEntity<>(new Success("카카오 로그인", kakaoService.kakaoLogin(code)), HttpStatus.OK);
    }
}
