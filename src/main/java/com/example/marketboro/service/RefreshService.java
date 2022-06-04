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
        String userPk = jwtTokenProvider.getUserPk(refreshToken);
        String getRefreshToken = redisService.getValues(userPk);
        User user = userRepository.findByUsername(userPk)
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));

        tokenValidation(refreshToken, accessToken, getRefreshToken, userPk);

        String updateToken = jwtTokenProvider.createToken(user.getUsername());
        String updateRefreshToken = jwtTokenProvider.createRefreshToken(user.getUsername());
        redisService.delValues(userPk);
        redisService.setValues(updateRefreshToken, userPk);

        return TokenResponseDto.builder()
                .accessToken(updateToken)
                .refreshToken(updateRefreshToken)
                .build();
    }

    private void tokenValidation(String refreshToken, String accessToken,
                                 String getRefreshToken, String userPk) {
        // 리프레시 토큰 기간 만료 에러
        if (!jwtTokenProvider.validateRefreshToken(refreshToken)) {
            throw new ErrorCustomException(ErrorCode.REFRESH_EXPIRATION_ERROR);
        }

        // 액세스 토큰이 만료 안됐을때 refresh 요청이 오면 해킹으로 간주
        if (jwtTokenProvider.validateRefreshToken(accessToken)) {
            redisService.delValues(userPk);
            throw new ErrorCustomException(ErrorCode.TOKEN_EXPIRATION_ERROR);
        }

        // redis 서버에 있는 refresh 토큰과 request에 포함된 refresh 토큰이 일치하지 않으면 해킹으로 간주
        if (!refreshToken.equals(getRefreshToken)) {
            throw new ErrorCustomException(ErrorCode.TOKEN_EXPIRATION_ERROR);
        }
    }

}