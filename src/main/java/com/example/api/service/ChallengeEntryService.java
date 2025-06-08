package com.example.api.service;

import com.example.api.dto.ChallengeEntryDto;
import com.example.api.mapper.ChallengeEntryRepository;
import com.example.api.mapper.ChallengeRepository;
import com.example.api.mapper.UserRepository;
import com.example.api.model.Challenge;
import com.example.api.model.ChallengeEntry;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChallengeEntryService {

    private final ChallengeEntryRepository challengeEntryRepository;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    @Transactional
    public ChallengeEntryDto.Response joinChallenge(Long userId, long challengeId) {
        if (challengeEntryRepository.existsByUserIdAndChallengeId(userId, challengeId)) {
            throw new IllegalArgumentException("You have already participated in that challenge.");
        }

        Challenge challenge = challengeRepository.findById(challengeId)
                .orElseThrow(() -> new IllegalArgumentException("Challenge not found."));
        ChallengeEntry entry = ChallengeEntry.builder()
                .challenge(challenge)
                .user(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found")))
                .totalDistanceKm(0f)
                .build();

        challengeEntryRepository.save(entry);

        return ChallengeEntryDto.Response.builder()
                .id(entry.getId())
                .challengeId(entry.getChallenge().getId())
                .userId(entry.getUser().getId())
                .totalDistanceKm(entry.getTotalDistanceKm())
                .build();
    }
}
