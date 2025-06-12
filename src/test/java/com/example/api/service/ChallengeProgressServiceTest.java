package com.example.api.service;

import com.example.api.dto.ChallengeProgressDto;
import com.example.api.mapper.ChallengeEntryRepository;
import com.example.api.mapper.ChallengeRepository;
import com.example.api.mapper.UserRepository;
import com.example.api.model.Challenge;
import com.example.api.model.ChallengeEntry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChallengeProgressServiceTest {

    @Mock
    private ChallengeRepository challengeRepository;

    @Mock
    private ChallengeEntryRepository challengeEntryRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ChallengeProgressService challengeProgressService;

    @Test
    void getProgress_shouldReturnCorrectProgressPercentage() {
        Long challengeId = 1L;
        Long userId = 1L;

        Challenge challenge = Challenge.builder()
                .id(challengeId)
                .goalDistanceKm(100.0f)
                .build();

        ChallengeEntry entry = ChallengeEntry.builder()
                .challenge(challenge)
                .totalDistanceKm(40.0f)
                .build();

        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(challenge));
        when(challengeEntryRepository.findByChallengeIdAndUserId(challengeId, userId)).thenReturn(Optional.of(entry));

        ChallengeProgressDto progress = challengeProgressService.getUserProgress(challengeId, userId);

        assertEquals(challengeId, progress.getChallengeId());
        assertEquals(userId, progress.getUserId());
        assertEquals(100.0f, progress.getGoalDistanceKm());
        assertEquals(40.0f, progress.getGoalDistanceKm());
        assertEquals(40.0f, progress.getProgressPercentage());
    }

    @Test
    void getProgress_shouldThrowIfEntryNotFound() {
        when(challengeRepository.findById(1L)).thenReturn(Optional.of(new Challenge()));
        when(challengeEntryRepository.findByChallengeIdAndUserId(1L, 999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                challengeProgressService.getUserProgress(1L, 999L));
    }
}
