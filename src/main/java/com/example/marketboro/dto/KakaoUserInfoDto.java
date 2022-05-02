package com.example.marketboro.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KakaoUserInfoDto {
    private String nickname;
    private String email;
    private String password;
}