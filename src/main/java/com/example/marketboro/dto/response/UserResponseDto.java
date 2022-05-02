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
        private String accesstoken;
        private String refreshtoken;

        @Builder
        public LoginResponseDto(final String username, final String name, final String nickname,
                                final String accesstoken, final String refreshtoken) {
            this.username = username;
            this.name = name;
            this.nickname = nickname;
            this.accesstoken = accesstoken;
            this.refreshtoken = refreshtoken;
        }
    }
}
