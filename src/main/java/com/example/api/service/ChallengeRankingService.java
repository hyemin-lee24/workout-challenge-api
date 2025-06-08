package com.example.api.service;

import com.example.api.dto.ChallengeStatsDto;
import com.example.api.mapper.ChallengeEntryRepository;
import com.example.api.model.ChallengeEntry;
import com.example.api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeRankingService {
    private final ChallengeEntryRepository challengeEntryRepository;

    @Transactional(readOnly = true)
    public List<ChallengeStatsDto.Ranking> getTop10Ranking(Long challengeId) {
        List<ChallengeEntry> entries = challengeEntryRepository.findByChallengeId(challengeId);

        return entries.stream()
                .sorted(Comparator.comparing(ChallengeEntry::getTotalDistanceKm).reversed())
                .limit(10)
                .map(entry -> {
                    User user = entry.getUser();
                    return ChallengeStatsDto.Ranking.builder()
                            .userId(user.getId())
                            .nickname(user.getNickname())
                            .totalDistanceKm(entry.getTotalDistanceKm())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChallengeStatsDto.Ranking getUserRank(Long challengeId, Long userId) {
        List<ChallengeEntry> entries = new ArrayList<>(challengeEntryRepository.findByChallengeId(challengeId));
        entries.sort(Comparator.comparing(ChallengeEntry::getTotalDistanceKm).reversed());

        for (int i = 0; i < entries.size(); i++) {
            if (entries.get(i).getUser().getId().equals(userId)) {
                User user = entries.get(i).getUser();
                return ChallengeStatsDto.Ranking.builder()
                        .userId(user.getId())
                        .nickname(user.getNickname())
                        .totalDistanceKm(entries.get(i).getTotalDistanceKm())
                        .ranking(i + 1)
                        .build();
            }
        }

        throw new RuntimeException("User not found in challenge entries");
    }
}