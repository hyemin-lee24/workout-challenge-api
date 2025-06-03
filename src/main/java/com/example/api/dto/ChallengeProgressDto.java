package com.example.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChallengeProgressDto {
    private Long challengeId;
    private Long userId;
    private String nickname;
    private Float goalDistanceKm;
    private Float totalDistanceKm;
    private Float progressPercentage;
}
