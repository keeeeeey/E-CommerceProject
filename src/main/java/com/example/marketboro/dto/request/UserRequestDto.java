package com.example.marketboro.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

public class UserRequestDto {

    @Getter
    @NoArgsConstructor
    public static class JoinRequestDto {

        @NotEmpty(message = "사용할 아이디를 입력해주세요.")
        private String username;

        @NotEmpty(message = "사용할 비밀번호를 입력해주세요.")
        private String password;

        @NotEmpty(message = "비밀번호가 일치하지 않습니다.")
        private String passwordcheck;

        @NotEmpty(message = "사용할 이름을 입력해주세요.")
        private String name;

        @NotEmpty(message = "사용할 닉네임을 입력해주세요.")
        private String nickname;
    }

    @Getter
    @NoArgsConstructor
    public static class LoginRequestDto {

        @NotEmpty(message = "아이디를 입력해주세요.")
        private String username;

        @NotEmpty(message = "비밀번호를 입력해주세요.")
        private String password;
    }

}
