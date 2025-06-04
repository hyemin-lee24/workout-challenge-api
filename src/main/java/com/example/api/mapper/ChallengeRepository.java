package com.example.api.mapper;

import com.example.api.code.ChallengeStatus;
import com.example.api.model.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ChallengeRepository extends JpaRepository<Challenge, Long> {
    List<Challenge> findByStatusAndEndDateBefore(ChallengeStatus ongoing, LocalDateTime now);
}
