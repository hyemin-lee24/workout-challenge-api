package com.example.api.service;

import com.example.api.dto.ChallengeStatsDto;
import com.example.api.mapper.ChallengeEntryRepository;
import com.example.api.mapper.ChallengeRepository;
import com.example.api.mapper.UserRepository;
import com.example.api.model.Challenge;
import com.example.api.model.ChallengeEntry;
import com.example.api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeStatsService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeEntryRepository challengeEntryRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ChallengeStatsDto.MyStats getMyStats(Long challengeId, Long userId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        ChallengeEntry entry = challengeEntryRepository.findByChallengeIdAndUserId(challengeId, userId)
                .orElseThrow(() -> new RuntimeException("Challenge entry not found"));

        float progress = (entry.getTotalDistanceKm() / challenge.getGoalDistanceKm()) * 100f;

        return ChallengeStatsDto.MyStats.builder()
                .challengeId(challengeId)
                .userId(userId)
                .totalDistanceKm(entry.getTotalDistanceKm())
                .progress(progress)
                .build();
    }

    @Transactional(readOnly = true)
    public ChallengeStatsDto.Summary getSummary(Long challengeId) {
        List<ChallengeEntry> entries = challengeEntryRepository.findByChallengeId(challengeId);

        float totalDistance = (float) entries.stream()
                .mapToDouble(ChallengeEntry::getTotalDistanceKm)
                .sum();

        float averageDistance = entries.isEmpty() ? 0 : totalDistance / entries.size();

        return ChallengeStatsDto.Summary.builder()
                .participantCount(entries.size())
                .totalDistanceKm(totalDistance)
                .averageDistanceKm(averageDistance)
                .build();
    }
}
