package com.example.marketboro.service;

import com.example.marketboro.dto.response.TokenResponseDto;
import com.example.marketboro.entity.User;
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
            throw new RuntimeException("로그인이 만료되었습니다.");
        }

        String userPk = jwtTokenProvider.getUserPk(refreshToken);
        String getRefreshToken = redisService.getValues(userPk);
        User user = userRepository.findByUsername(userPk)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 유저입니다."));

        if (jwtTokenProvider.validateToken(accessToken)) {
            redisService.delValues(userPk);
            throw new RuntimeException("다시 로그인 해주세요.");
        }

        if (!refreshToken.equals(getRefreshToken)) {
            throw new RuntimeException("다시 로그인 해주세요.");
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