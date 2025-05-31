package com.example.api.service;

import com.example.api.dto.ChallengeDto;
import com.example.api.mapper.ChallengeRepository;
import com.example.api.model.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional(readOnly = true)
    public List<ChallengeDto.Response> getAllChallenges() {
        List<Challenge> challenges = challengeRepository.findAll();

        return challenges.stream()
                .map(challenge -> ChallengeDto.Response.builder()
                        .id(challenge.getId())
                        .title(challenge.getTitle())
                        .description(challenge.getDescription())
                        .startDate(challenge.getStartDate())
                        .endDate(challenge.getEndDate())
                        .goalDistanceKm(challenge.getGoalDistanceKm())
                        .build())
                .collect(Collectors.toList());
    }
}
