package com.bosszhipin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private Long userId;
    private String username;
    private String token;
    private String tokenType;
    private Long expiresIn;

    public static LoginResponse of(Long userId, String username, String token, Long expiresIn) {
        return LoginResponse.builder()
                .userId(userId)
                .username(username)
                .token(token)
                .tokenType("Bearer")
                .expiresIn(expiresIn)
                .build();
    }
}
