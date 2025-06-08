package com.example.api.service;

import com.example.api.dto.ChallengeStatsDto;
import com.example.api.mapper.ChallengeEntryRepository;
import com.example.api.model.ChallengeEntry;
import com.example.api.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ChallengeRankingServiceTest {

    @Mock
    private ChallengeEntryRepository challengeEntryRepository;

    @InjectMocks
    private ChallengeRankingService challengeRankingService;

    @Test
    void getTop10Ranking() {
        List<ChallengeEntry> mockEntries = IntStream.range(1, 21)
                .mapToObj(i -> {
                    User user = User.builder()
                            .id((long) i)
                            .nickname("test" + i)
                            .build();
                    return ChallengeEntry.builder()
                            .user(user)
                            .totalDistanceKm(100.0f - i)
                            .build();
                })
                .collect(Collectors.toList());

        when(challengeEntryRepository.findByChallengeId(1L))
                .thenReturn(mockEntries);

        List<ChallengeStatsDto.Ranking> result = challengeRankingService.getTop10Ranking(1L);

        assertEquals(10, result.size());
        assertEquals("test1", result.get(0).getNickname());
        assertEquals("test10", result.get(9).getNickname());
    }
}

