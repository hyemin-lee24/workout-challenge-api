package com.example.api.mapper;

import com.example.api.model.ChallengeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChallengeEntryRepository extends JpaRepository<ChallengeEntry, Long> {
    boolean existsByUserIdAndChallengeId(Long userId, Long challengeId);
}
