package com.example.marketboro;

import com.example.marketboro.entity.User;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.security.UserDetailsImpl;

import java.util.Optional;

public class Validator {
    public static void authenticationValidator(UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
    }

    public static void joinValidation(Optional<User> findUser, String password,
                                String passwordCheck, Optional<User> findUserByNickname) {
        if (findUser.isPresent()) {
            throw new ErrorCustomException(ErrorCode.ALREADY_USERNAME_ERROR);
        }

        if (!password.equals(passwordCheck)) {
            throw new ErrorCustomException(ErrorCode.NO_MATCH_PASSWORD_ERROR);
        }

        if (findUserByNickname.isPresent()) {
            throw new ErrorCustomException(ErrorCode.ALREADY_NICKNAME_ERROR);
        }
    }
}
