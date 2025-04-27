package com.example.api.dto;

import com.example.api.model.User;
import lombok.*;

import java.time.LocalDateTime;

public class UserDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUpRequest {
        private String email;
        private String password;
        private String nickname;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class LoginRequest {
        private String email;
        private String password;
    }

    @Getter
    @Setter
    @Builder
    public static class UserResponse {
        private Long id;
        private String email;
        private String nickname;
        private String token;  // 로그인 시 JWT 토큰 반환
        private LocalDateTime createdAt;

        public static UserResponse from(User user) {
            return UserResponse.builder()
                    .id(user.getId())
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .createdAt(user.getCreatedAt())
                    .build();
        }
    }
}
