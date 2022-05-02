package com.example.marketboro.service;

import com.example.marketboro.dto.response.TokenResponseDto;
import com.example.marketboro.entity.User;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.repository.UserRepository;
import com.example.marketboro.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Transactional
    public TokenResponseDto refresh(String accessToken, String refreshToken) {
        // 리프레시 토큰 기간 만료 에러
        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new ErrorCustomException(ErrorCode.REFRESH_EXPIRATION_ERROR);
        }

        String userPk = jwtTokenProvider.getUserPk(refreshToken);
        String getRefreshToken = redisService.getValues(userPk);
        User user = userRepository.findByUsername(userPk)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));

        if (jwtTokenProvider.validateToken(accessToken)) {
            redisService.delValues(userPk);
            throw new ErrorCustomException(ErrorCode.TOKEN_EXPIRATION_ERROR);
        }

        if (!refreshToken.equals(getRefreshToken)) {
            throw new ErrorCustomException(ErrorCode.TOKEN_EXPIRATION_ERROR);
        }

        String updateToken = jwtTokenProvider.createToken(user.getUsername());
        String updateRefreshToken = jwtTokenProvider.createRefreshToken(user.getUsername());
        redisService.delValues(userPk);
        redisService.setValues(updateRefreshToken, userPk);

        return TokenResponseDto.builder()
                .accesstoken(updateToken)
                .refreshtoken(updateRefreshToken)
                .build();
    }

}