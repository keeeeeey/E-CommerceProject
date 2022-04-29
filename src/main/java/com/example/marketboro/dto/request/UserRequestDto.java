package com.example.marketboro.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserRequestDto {

    @Getter
    @NoArgsConstructor
    public static class JoinRequestDto {
        private String username;
        private String password;
        private String passwordcheck;
        private String name;
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    public static class LoginRequestDto {
        private String username;
        private String password;
    }

}
