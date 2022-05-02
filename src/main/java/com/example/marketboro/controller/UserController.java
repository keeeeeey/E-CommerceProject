package com.example.marketboro.controller;

import com.example.marketboro.dto.Success;
import com.example.marketboro.dto.request.UserRequestDto.JoinRequestDto;
import com.example.marketboro.dto.request.UserRequestDto.LoginRequestDto;
import com.example.marketboro.service.RefreshService;
import com.example.marketboro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RefreshService refreshService;

    @PostMapping("/user/join")
    public ResponseEntity<Success> join(@RequestBody JoinRequestDto requestDto) {
        return new ResponseEntity<>(new Success("회원가입",
                userService.join(requestDto)), HttpStatus.OK);
    }

    @PostMapping("/user/login")
    public ResponseEntity<Success> login(@RequestBody LoginRequestDto requestDto) {
        return new ResponseEntity<>(new Success("로그인",
                userService.login(requestDto)), HttpStatus.OK);
    }

    @PostMapping("/user/refresh")
    public ResponseEntity<Success> refresh(@RequestHeader(value = "AccessAuthorization") String accessToken,
                                           @RequestHeader(value = "RefreshAuthorization") String refreshToken) {
        return new ResponseEntity<>(new Success<>(
                "토큰 재발급 성공", refreshService.refresh(accessToken, refreshToken)), HttpStatus.OK);
    }
}
