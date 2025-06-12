package com.ssafy.Daangn.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class TokenResponseDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long tokenExpiresIn;
    private Long refreshTokenExpiresIn;
    private Integer userId;
    private String nickname;
}
