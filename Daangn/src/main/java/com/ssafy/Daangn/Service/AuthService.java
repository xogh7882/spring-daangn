package com.ssafy.Daangn.Service;

import com.ssafy.Daangn.Domain.User;
import com.ssafy.Daangn.Dto.LoginRequestDto;
import com.ssafy.Daangn.Dto.SignupRequestDto;
import com.ssafy.Daangn.Dto.TokenResponseDto;
import com.ssafy.Daangn.Jwt.TokenProvider;
import com.ssafy.Daangn.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public TokenResponseDto login(LoginRequestDto loginRequest) {
        User user = userRepository.findByUserId(loginRequest.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저"));

        if(!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("비밀번호 오류");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getId().toString(), null,
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"))
        );

        String accessToken = tokenProvider.createAccessToken(user.getId(), authentication);

        return TokenResponseDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .tokenExpiresIn(1800L)
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();
    }

    public User singup(SignupRequestDto signupRequest) {
        if(userRepository.findByUserId(signupRequest.getUserId()).isPresent()){
            throw new IllegalArgumentException("이미 존재하는 ID");
        }

        User user = new User();
        user.setUserId(signupRequest.getUserId());
        user.setNickname(signupRequest.getNickname());
        user.setPassword(signupRequest.getPassword());
        user.setName(signupRequest.getName());

        user.setDegree(36.5);
        user.setLocation(null);
        user.setImage(null);


        return userRepository.save(user);
    }
}
