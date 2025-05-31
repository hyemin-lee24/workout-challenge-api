package com.example.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ChallengeEntryDto {

    @Getter
    @Setter
    public static class Request {
        private Long challengeId;
    }

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private Long challengeId;
        private Long userId;
        private Float totalDistanceKm;
    }
}
