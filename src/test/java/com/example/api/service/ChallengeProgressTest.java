package com.example.api.service;

import com.example.api.code.ChallengeStatus;
import com.example.api.dto.ChallengeStatsDto;
import com.example.api.mapper.ChallengeEntryRepository;
import com.example.api.mapper.ChallengeRepository;
import com.example.api.mapper.UserRepository;
import com.example.api.model.Challenge;
import com.example.api.model.ChallengeEntry;
import com.example.api.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
class ChallengeProgressTest {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ChallengeEntryRepository challengeEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChallengeStatsService challengeStatsService;

    private User user;
    private Challenge challenge;

    @BeforeEach
    void setUp() {
        user = userRepository.save(User.builder()
                .email("progress@test.com")
                .password("1234")
                .nickname("runner")
                .build());

        challenge = challengeRepository.save(Challenge.builder()
                .title("50km in a Week")
                .goalDistanceKm(50f)
                .createdAt(LocalDateTime.now().minusDays(1))
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(5))
                .status(ChallengeStatus.ONGOING)
                .build());

        challengeEntryRepository.save(ChallengeEntry.builder()
                .user(user)
                .challenge(challenge)
                .totalDistanceKm(25f)
                .build());
    }

    @Test
    void shouldReturnCorrectProgress() {
        ChallengeStatsDto.MyStats progress = challengeStatsService.getMyStats(challenge.getId(), user.getId());

        assertEquals(50f, progress.getProgress(), 0.01);
    }
}
