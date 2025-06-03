package com.example.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class ChallengeStatsDto {

    @Getter
    @Builder
    public static class MyStats {
        private Long challengeId;
        private Long userId;
        private float totalDistanceKm;
        private float progress;
    }

    @Getter
    @Setter
    @Builder
    public static class Ranking {
        private Long userId;
        private String nickname;
        private Float totalDistanceKm;
        private int ranking;
    }

    @Getter
    @Builder
    public static class Summary {
        private int participantCount;
        private float totalDistanceKm;
        private float averageDistanceKm;
    }
}
