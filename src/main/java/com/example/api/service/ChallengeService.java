package com.example.api.service;

import com.example.api.dto.ChallengeDto;
import com.example.api.mapper.ChallengeRepository;
import com.example.api.model.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;

    public ChallengeDto.Response createChallenge(ChallengeDto.Request request) {
        Challenge challenge = Challenge.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .goalDistanceKm(request.getGoalDistanceKm())
                .createdAt(LocalDateTime.now())
                .build();
        Challenge saved = challengeRepository.save(challenge);

        return ChallengeDto.Response.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .startDate(saved.getStartDate())
                .endDate(saved.getEndDate())
                .goalDistanceKm(saved.getGoalDistanceKm())
                .build();
    }
}
