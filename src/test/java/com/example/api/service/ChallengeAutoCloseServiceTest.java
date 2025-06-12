package com.example.api.service;

import com.example.api.code.ChallengeStatus;
import com.example.api.component.ChallengeScheduler;
import com.example.api.mapper.ChallengeRepository;
import com.example.api.model.Challenge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChallengeSchedulerTest {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ChallengeScheduler challengeScheduler;

    @Test
    void closeEndedChallenges_shouldCloseChallengesPastEndDate() {
        Challenge endedChallenge = Challenge.builder()
                .title("Past Challenge")
                .startDate(LocalDateTime.now().minusDays(3))
                .endDate(LocalDateTime.now().minusDays(1))
                .status(ChallengeStatus.ONGOING)
                .createdAt(LocalDateTime.now().minusDays(3))
                .build();

        Challenge activeChallenge = Challenge.builder()
                .title("Future Challenge")
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(1))
                .status(ChallengeStatus.ONGOING)
                .createdAt(LocalDateTime.now())
                .build();

        challengeRepository.saveAll(List.of(endedChallenge, activeChallenge));

        challengeScheduler.closeEndedChallenges();

        List<Challenge> allChallenges = challengeRepository.findAll();

        Challenge ended = allChallenges.stream()
                .filter(c -> c.getTitle().equals("Past Challenge"))
                .findFirst()
                .orElseThrow();

        Challenge active = allChallenges.stream()
                .filter(c -> c.getTitle().equals("Future Challenge"))
                .findFirst()
                .orElseThrow();

        assertEquals(ChallengeStatus.ENDED, ended.getStatus());
        assertEquals(ChallengeStatus.ONGOING, active.getStatus());
    }
}


