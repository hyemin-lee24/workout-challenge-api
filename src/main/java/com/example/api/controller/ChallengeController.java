package com.example.api.controller;

import com.example.api.config.UserPrincipal;
import com.example.api.dto.ChallengeDto;
import com.example.api.dto.ChallengeStatsDto;
import com.example.api.service.ChallengeService;
import com.example.api.service.ChallengeStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.IntStream;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final ChallengeStatsService challengeStatsService;

    @GetMapping
    public ResponseEntity<List<ChallengeDto.Response>> getChallenges() {
        List<ChallengeDto.Response> challenges = challengeService.getAllChallenges();
        return ResponseEntity.ok(challenges);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ChallengeDto.Response> createChallenge(@RequestBody ChallengeDto.Request request) {
        ChallengeDto.Response response = challengeService.createChallenge(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{challengeId}/my-stats")
    public ResponseEntity<ChallengeStatsDto.MyStats> getMyStats(
            @PathVariable Long challengeId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        ChallengeStatsDto.MyStats stats = challengeStatsService.getMyStats(challengeId, userPrincipal.getId());
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/{challengeId}/ranking")
    public ResponseEntity<List<ChallengeStatsDto.RankingEntry>> getRanking(
            @PathVariable Long challengeId
    ) {
        List<ChallengeStatsDto.RankingEntry> ranking = challengeStatsService.getRanking(challengeId);

        IntStream.range(0, ranking.size()).forEach(i -> ranking.get(i).setRank(i + 1));

        return ResponseEntity.ok(ranking);
    }
}
