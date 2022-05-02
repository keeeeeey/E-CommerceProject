package com.example.marketboro.service;

import com.example.marketboro.dto.KakaoUserInfoDto;
import com.example.marketboro.dto.response.UserResponseDto;
import com.example.marketboro.dto.response.UserResponseDto.LoginResponseDto;
import com.example.marketboro.entity.User;
import com.example.marketboro.entity.UserRoleEnum;
import com.example.marketboro.repository.UserRepository;
import com.example.marketboro.security.JwtTokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Transactional
    public LoginResponseDto kakaoLogin(String code) throws JsonProcessingException {
        // 1. "인가 코드"로 "액세스 토큰" 요청
        String accessToken = getAccessToken(code);

        // 2. 토큰으로 카카오 API 호출
        KakaoUserInfoDto kakaoUserInfo = getKakaoUserInfo(accessToken);

        // 3. 필요시에 회원가입, JWT 토큰 발행
        return registerKakaoUserIfNeeded(kakaoUserInfo);
    }

    private String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", "b6444d179d6392c0a081f448f8528542");
        body.add("redirect_uri", "http://localhost:8081/user/kakao/callback");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    private KakaoUserInfoDto getKakaoUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        Long id = jsonNode.get("id").asLong();
        String nickname = jsonNode.get("properties")
                .get("nickname").asText();
        String email = jsonNode.get("kakao_account")
                .get("email").asText();
        String password = UUID.randomUUID().toString();

        System.out.println("카카오 사용자 정보: " + id + ", " + nickname + ", " + email);
        return new KakaoUserInfoDto(id, nickname, email, password);
    }

    private LoginResponseDto registerKakaoUserIfNeeded(KakaoUserInfoDto kakaoUserInfo) {
        // DB 에 중복된 Kakao Id 가 있는지 확인
        String username = kakaoUserInfo.getEmail();

        User kakaoUser = userRepository.findByUsername(username)
                .orElse(null);

        if (kakaoUser == null) {
            // 회원가입
            String nickname = kakaoUserInfo.getNickname();
            String password = kakaoUserInfo.getPassword();

            kakaoUser = User.builder()
                    .username(username)
                    .password(password)
                    .name(nickname)
                    .nickname(nickname)
                    .role(UserRoleEnum.USER)
                    .build();
            userRepository.save(kakaoUser);
        }

        String accessToken = jwtTokenProvider.createToken(kakaoUser.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(kakaoUser.getUsername());
        redisService.setValues(refreshToken, kakaoUser.getUsername());
        return LoginResponseDto.builder()
                .username(kakaoUser.getUsername())
                .name(kakaoUser.getName())
                .nickname(kakaoUser.getNickname())
                .accesstoken(accessToken)
                .refreshtoken(refreshToken)
                .build();
    }
}