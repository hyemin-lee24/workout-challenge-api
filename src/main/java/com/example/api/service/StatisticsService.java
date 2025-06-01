package com.example.api.service;

import com.example.api.dto.StatisticsSummaryDto;
import com.example.api.mapper.WorkoutDataRepository;
import com.example.api.model.WorkoutData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final WorkoutDataRepository workoutDataRepository;

    public StatisticsSummaryDto getUserSummary(Long userId) {
        List<WorkoutData> dataList = workoutDataRepository.findByUserIdAndDeletedFalse(userId);

        float totalDistance = 0f;
        float totalCalories = 0f;
        int totalDuration = 0;

        for (WorkoutData data : dataList) {
            totalDistance += data.getDistanceKm();
            totalCalories += data.getCaloriesBurned();
            totalDuration += data.getDurationSeconds();
        }

        return StatisticsSummaryDto.builder()
                .totalDistanceKm(totalDistance)
                .totalCalories(totalCalories)
                .totalDurationSeconds(totalDuration)
                .build();
    }
}