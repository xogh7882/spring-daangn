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
    private Long tokenExpiresIn;
    private Integer userId;
    private String nickname;
}
