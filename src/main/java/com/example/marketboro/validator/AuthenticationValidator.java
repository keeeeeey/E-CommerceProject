package com.example.marketboro.validator;

import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.security.UserDetailsImpl;

public class AuthenticationValidator {
    public static void authenticationValidator(UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new ErrorCustomException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }
    }
}
