package com.example.api.service;

import com.example.api.dto.ChallengeEntryDto;
import com.example.api.mapper.ChallengeEntryRepository;
import com.example.api.mapper.ChallengeRepository;
import com.example.api.mapper.UserRepository;
import com.example.api.model.Challenge;
import com.example.api.model.ChallengeEntry;
import com.example.api.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChallengeEntryServiceTest {

    @InjectMocks
    private ChallengeEntryService challengeEntryService;

    @Mock
    private ChallengeEntryRepository challengeEntryRepository;

    @Mock
    private ChallengeRepository challengeRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    void joinChallenge() {
        long userId = 1;
        long challengeId = 1;

        User user = User.builder().id(userId).nickname("test1").build();
        Challenge challenge = Challenge.builder().id(challengeId).title("5월 러닝 챌린지").build();

        ChallengeEntry savedEntry = ChallengeEntry.builder()
                .id(challengeId)
                .user(user)
                .challenge(challenge)
                .totalDistanceKm(0f)
                .build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(challengeRepository.findById(challengeId)).thenReturn(Optional.of(challenge));
        when(challengeEntryRepository.save(any(ChallengeEntry.class))).thenReturn(savedEntry);

        ChallengeEntryDto.Response response = challengeEntryService.joinChallenge(userId, challengeId);

        assertNotNull(response);
        assertEquals(userId, response.getUserId());
        assertEquals(challengeId, response.getChallengeId());
        assertEquals(0f, response.getTotalDistanceKm());

        verify(challengeEntryRepository).save(any(ChallengeEntry.class));
    }
}
