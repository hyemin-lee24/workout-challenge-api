package com.example.api.service;

import com.example.api.dto.ChallengeProgressDto;
import com.example.api.mapper.ChallengeEntryRepository;
import com.example.api.mapper.ChallengeRepository;
import com.example.api.mapper.UserRepository;
import com.example.api.model.Challenge;
import com.example.api.model.ChallengeEntry;
import com.example.api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChallengeProgressService {

    private final ChallengeRepository challengeRepository;
    private final ChallengeEntryRepository challengeEntryRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public ChallengeProgressDto getUserProgress(Long challengeId, Long userId) {
        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new RuntimeException("Challenge not found"));

        ChallengeEntry entry = challengeEntryRepository.findByChallengeIdAndUserId(challengeId, userId)
                .orElseThrow(() -> new RuntimeException("Entry not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Float progress = (entry.getTotalDistanceKm() / challenge.getGoalDistanceKm()) * 100f;

        return ChallengeProgressDto.builder()
                .challengeId(challengeId)
                .userId(userId)
                .nickname(user.getNickname())
                .goalDistanceKm(challenge.getGoalDistanceKm())
                .totalDistanceKm(entry.getTotalDistanceKm())
                .progressPercentage(Math.min(progress, 100f))
                .build();
    }
}

