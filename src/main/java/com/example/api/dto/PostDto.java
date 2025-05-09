package com.example.api.dto;


import com.example.api.model.Post;
import com.example.api.model.User;
import lombok.*;
import java.time.LocalDateTime;

public class PostDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CreateRequest {
        private Long user;
        private String title;
        private String content;
        private String imageUrl;
    }

    @Getter
    @Setter
    @Builder
    public static class PostResponse {
        private Long id;
        private User userId;
        private String title;
        private String content;
        private String imageUrl;
        private LocalDateTime createdAt;
        private String authorNickname;

        public static PostDto.PostResponse from(Post post) {
            return PostDto.PostResponse.builder()
                    .id(post.getId())
                    .userId(post.getUser())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .imageUrl(post.getImageUrl())
                    .createdAt(post.getCreatedAt())
                    .build();
        }
    }
}
