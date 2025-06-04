package com.example.api.component;

import com.example.api.code.ChallengeStatus;
import com.example.api.mapper.ChallengeRepository;
import com.example.api.model.Challenge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ChallengeSchedulerTest {

    @Autowired
    private ChallengeRepository challengeRepository;

    @Autowired
    private ChallengeScheduler challengeScheduler;

    @Test
    void closeEndedChallenges_shouldSetStatusToEnded_whenEndDateIsBeforeNow() {
        // given
        Challenge pastChallenge = Challenge.builder()
                .title("Last challenge")
                .description("This challenge must end.")
                .startDate(LocalDateTime.now().minusDays(10))
                .endDate(LocalDateTime.now().minusDays(1))
                .goalDistanceKm(10f)
                .status(ChallengeStatus.ONGOING)
                .createdAt(LocalDateTime.now())
                .build();

        challengeRepository.save(pastChallenge);
        challengeScheduler.closeEndedChallenges();
        Challenge updated = challengeRepository.findById(pastChallenge.getId())
                .orElseThrow();
        assertThat(updated.getStatus()).isEqualTo(ChallengeStatus.ENDED);
    }

    @Test
    void closeEndedChallenges_shouldNotAffectFutureChallenges() {
        // given
        Challenge futureChallenge = Challenge.builder()
                .title("Future Challenge")
                .description("It should not be shut down yet.")
                .startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now().plusDays(10))
                .goalDistanceKm(20f)
                .status(ChallengeStatus.ONGOING)
                .createdAt(LocalDateTime.now())
                .build();

        challengeRepository.save(futureChallenge);
        challengeScheduler.closeEndedChallenges();
        Challenge updated = challengeRepository.findById(futureChallenge.getId())
                .orElseThrow();
        assertThat(updated.getStatus()).isEqualTo(ChallengeStatus.ONGOING);
    }
}
