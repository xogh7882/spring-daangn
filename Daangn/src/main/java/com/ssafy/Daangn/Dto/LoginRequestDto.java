package com.ssafy.Daangn.Dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequestDto {

    private String userId;
    private String password;

    public static LoginRequestDto of(String userId, String password) {
        return new LoginRequestDto(userId, password);
    }

    public static LoginRequestDto TestLogin(){
        return new LoginRequestDto("test","1234");
    }
}
