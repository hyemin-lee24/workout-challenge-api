package com.example.api.component;

import com.example.api.code.ChallengeStatus;
import com.example.api.mapper.ChallengeRepository;
import com.example.api.model.Challenge;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeScheduler {

    private final ChallengeRepository challengeRepository;

    @Transactional
    @Scheduled(cron = "0 0 * * * *")
    public void closeEndedChallenges() {
        List<Challenge> challengesToClose = challengeRepository
                .findByStatusAndEndDateBefore(ChallengeStatus.ONGOING, LocalDateTime.now());

        for (Challenge challenge : challengesToClose) {
            challenge.setStatus(ChallengeStatus.ENDED);
        }
    }
}
