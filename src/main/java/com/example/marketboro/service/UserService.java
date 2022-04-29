package com.example.marketboro.service;

import com.example.marketboro.dto.request.UserRequestDto;
import com.example.marketboro.dto.request.UserRequestDto.JoinRequestDto;
import com.example.marketboro.dto.request.UserRequestDto.LoginRequestDto;
import com.example.marketboro.dto.response.UserResponseDto;
import com.example.marketboro.dto.response.UserResponseDto.LoginResponseDto;
import com.example.marketboro.entity.User;
import com.example.marketboro.repository.UserRepository;
import com.example.marketboro.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
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

    //회원가입 확인
    @Transactional
    public Long join(JoinRequestDto requestDto) {
        // 회원 ID 중복 확인
        String username = requestDto.getUsername();
        Optional<User> findUser = userRepository.findByUsername(username);

        if (findUser.isPresent()) {
            throw new RuntimeException("이미 사용중인 아이디입니다.");
        }

        String password = requestDto.getPassword();
        String passwordCheck = requestDto.getPasswordcheck();

        if (!password.equals(passwordCheck)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String nickname = requestDto.getNickname();
        Optional<User> findUserByNickname = userRepository.findByNickname(nickname);

        if (findUserByNickname.isPresent()) {
            throw new RuntimeException("이미 사용중인 닉네임입니다.");
        }

        // 패스워드 암호화
        String bcryptpassword = passwordEncoder.encode(password);

        User user = User.builder()
                .username(username)
                .password(bcryptpassword)
                .name(requestDto.getName())
                .nickname(nickname)
                .build();

        userRepository.save(user);
        return user.getId();
    }

    //로그인
    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        String accesstoken = jwtTokenProvider.createToken(user.getUsername());

        return LoginResponseDto.builder()
                .username(user.getUsername())
                .name(user.getName())
                .nickname(user.getNickname())
                .accesstoken(accesstoken)
                .build();
    }
}
