package com.example.api.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatisticsSummaryDto {
    private float totalDistanceKm;
    private float totalCalories;
    private int totalDurationSeconds;
}