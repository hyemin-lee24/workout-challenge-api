package com.example.api.service;

import com.example.api.code.ChallengeStatus;
import com.example.api.dto.ChallengeDto;
import com.example.api.mapper.ChallengeRepository;
import com.example.api.model.Challenge;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
class ChallengeServiceTest {

    @Autowired
    private ChallengeService challengeService;

    @Autowired
    private ChallengeRepository challengeRepository;

    @Test
    void createChallenge() {
        ChallengeDto.Request request = ChallengeDto.Request.builder()
                .title("New Challenge")
                .description("Challenge description")
                .goalDistanceKm(100f)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(7))
                .build();

        ChallengeDto.Response saved = challengeService.createChallenge(request);

        assertNotNull(saved.getId());
        assertEquals("New Challenge", saved.getTitle());
    }

    @Test
    void getAllChallenges() {
        Challenge challenge1 = Challenge.builder()
                .title("Challenge 1")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(5))
                .createdAt(LocalDateTime.now())
                .status(ChallengeStatus.ONGOING)
                .build();

        Challenge challenge2 = Challenge.builder()
                .title("Challenge 2")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(5))
                .createdAt(LocalDateTime.now())
                .status(ChallengeStatus.ONGOING)
                .build();

        challengeRepository.saveAll(List.of(challenge1, challenge2));

        List<ChallengeDto.Response> all = challengeService.getAllChallenges();

        assertEquals(2, all.size());
    }
}
