package com.example.marketboro.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserResponseDto {

    @Getter
    @NoArgsConstructor
    public static class LoginResponseDto {
        private String username;
        private String name;
        private String nickname;
        private String accessToken;
        private String refreshToken;

        @Builder
        public LoginResponseDto(final String username, final String name, final String nickname,
                                final String accessToken, final String refreshToken) {
            this.username = username;
            this.name = name;
            this.nickname = nickname;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
