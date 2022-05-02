package com.example.marketboro.service;

import com.example.marketboro.dto.request.UserRequestDto.JoinRequestDto;
import com.example.marketboro.dto.request.UserRequestDto.LoginRequestDto;
import com.example.marketboro.dto.response.UserResponseDto.LoginResponseDto;
import com.example.marketboro.entity.Cart;
import com.example.marketboro.entity.User;
import com.example.marketboro.entity.UserRoleEnum;
import com.example.marketboro.exception.ErrorCode;
import com.example.marketboro.exception.ErrorCustomException;
import com.example.marketboro.repository.CartRepository;
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
    public Long join(JoinRequestDto requestDto) {
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        Optional<User> findUser = userRepository.findByUsername(username);

        if (findUser.isPresent()) {
            throw new ErrorCustomException(ErrorCode.ALREADY_USERNAME_ERROR);
        }

        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordcheck();

        if (!password.equals(passwordCheck)) {
            throw new ErrorCustomException(ErrorCode.NO_MATCH_PASSWORD_ERROR);
        }

        String nickname = requestDto.getNickname();
        Optional<User> findUserByNickname = userRepository.findByNickname(nickname);

        if (findUserByNickname.isPresent()) {
            throw new ErrorCustomException(ErrorCode.ALREADY_NICKNAME_ERROR);
        }

        // 패스워드 암호화
        String bcryptpassword = passwordEncoder.encode(password);

        User user = User.builder()
                .username(username)
                .password(bcryptpassword)
                .name(requestDto.getName())
                .nickname(nickname)
                .role(UserRoleEnum.USER)
                .build();

        userRepository.save(user);

        Cart cart = Cart.builder()
                .user(user)
                .build();

        cartRepository.save(cart);

        return user.getId();
    }

    //로그인
    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new ErrorCustomException(ErrorCode.NO_USER_ERROR));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new ErrorCustomException(ErrorCode.NO_MATCH_PASSWORD_ERROR);
        }

        String accesstoken = jwtTokenProvider.createToken(user.getUsername());
        String refreshtoken = jwtTokenProvider.createRefreshToken(user.getUsername());
        redisService.setValues(refreshtoken, user.getUsername());

        return LoginResponseDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .nickname(user.getNickname())
                .accesstoken(accesstoken)
                .refreshtoken(refreshtoken)
                .build();
    }
}
