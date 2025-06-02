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
    public static class RankingEntry {
        private int rank;
        private Long userId;
        private String nickname;
        private float totalDistanceKm;
    }
}
