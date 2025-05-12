package com.example.api.dto;

import com.example.api.model.WorkoutData;
import lombok.*;

public class WorkoutDataDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WorkoutDataRequest {
        private Long postId;
        private float distanceKm; // 운동 거리 (km)
        private int durationSeconds; // 운동 시간 (초)
        private float avgSpeed; // 평균 속도
        private float caloriesBurned; // 소모 칼로리
        private String locationData; // 위치 데이터 (JSON)
    }

    @Getter
    @Setter
    @Builder
    public static class WorkoutDataResponse {
        private float distanceKm;
        private int durationSeconds;
        private float avgSpeed;
        private float caloriesBurned;
        private String locationData;

        public static WorkoutDataResponse from(WorkoutData workoutData) {
            return WorkoutDataResponse.builder()
                    .distanceKm(workoutData.getDistanceKm())
                    .durationSeconds(workoutData.getDurationSeconds())
                    .avgSpeed(workoutData.getAvgSpeed())
                    .caloriesBurned(workoutData.getCaloriesBurned())
                    .locationData(workoutData.getLocationData())
                    .build();
        }
    }
}
