package com.example.api.dto;


import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.model.WorkoutData;
import lombok.*;
import java.time.LocalDateTime;

public class PostDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostRequest {
        private String title;
        private String content;
        private String imageUrl;
        private WorkoutDataDto.WorkoutDataRequest workoutData;
    }

    @Getter
    @Setter
    @Builder
    public static class PostResponse {
        private Long id;
        private String title;
        private String content;
        private String imageUrl;
        private LocalDateTime createdAt;
        private User user;
        private WorkoutDataDto.WorkoutDataResponse workoutData;

        public static PostResponse from(Post post, WorkoutData workoutData) {
            return PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .imageUrl(post.getImageUrl())
                    .workoutData(WorkoutDataDto.WorkoutDataResponse.from(workoutData))
                    .build();
        }
    }
}
