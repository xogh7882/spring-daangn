package com.ssafy.Daangn.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SignupRequestDto {
    private String userId;
    private String password;
    private String nickname;
    private String name;
}
