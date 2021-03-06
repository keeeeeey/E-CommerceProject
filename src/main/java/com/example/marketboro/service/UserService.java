package com.example.marketboro.service;

import com.example.marketboro.Validator;
import com.example.marketboro.dto.request.UserRequestDto.JoinRequestDto;
import com.example.marketboro.dto.request.UserRequestDto.LoginRequestDto;
import com.example.marketboro.dto.response.UserResponseDto.LoginResponseDto;
import com.example.marketboro.entity.Cart;
import com.example.marketboro.entity.User;
import com.example.marketboro.entity.UserRoleEnum;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.repository.cart.CartRepository;
import com.example.marketboro.repository.UserRepository;
import com.example.marketboro.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CartRepository cartRepository;
    private final RedisService redisService;

    //회원가입 확인
    @Transactional
    public User join(JoinRequestDto requestDto) {
        String username = requestDto.getUsername();
        Optional<User> findUser = userRepository.findByUsername(username);

        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordCheck();

        String nickname = requestDto.getNickname();
        Optional<User> findUserByNickname = userRepository.findByNickname(nickname);

        Validator.joinValidation(findUser, password, passwordCheck, findUserByNickname);

        // 패스워드 암호화
        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .username(username)
                .password(encodedPassword)
                .name(requestDto.getName())
                .nickname(nickname)
                .role(UserRoleEnum.USER)
                .build();

        userRepository.save(user);

        Cart cart = Cart.builder()
                .user(user)
                .build();

        cartRepository.save(cart);

        return user;
    }

    //로그인
    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ErrorCustomException(ErrorCode.NO_MATCH_PASSWORD_ERROR);
        }

        String accessToken = jwtTokenProvider.createToken(user.getUsername());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getUsername());
        redisService.setValues(refreshToken, user.getUsername());

        return LoginResponseDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .nickname(user.getNickname())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
