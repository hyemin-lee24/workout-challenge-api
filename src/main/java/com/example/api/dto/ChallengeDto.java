package com.example.api.dto;

import lombok.*;

import java.time.LocalDateTime;

public class ChallengeDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        private String title;
        private String description;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
        private float goalDistanceKm;
    }

    @Getter
    @Setter
    @Builder
    public static class Response {
        private Long id;
        private String title;
        private String description;
        private float goalDistanceKm;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
    }
}
