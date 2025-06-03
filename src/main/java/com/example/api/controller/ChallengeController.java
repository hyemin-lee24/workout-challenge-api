package com.example.api.controller;

import com.example.api.config.UserPrincipal;
import com.example.api.dto.ChallengeDto;
import com.example.api.dto.ChallengeProgressDto;
import com.example.api.dto.ChallengeStatsDto;
import com.example.api.service.ChallengeProgressService;
import com.example.api.service.ChallengeRankingService;
import com.example.api.service.ChallengeService;
import com.example.api.service.ChallengeStatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenges")
public class ChallengeController {

    private final ChallengeService challengeService;
    private final ChallengeStatsService challengeStatsService;
    private final ChallengeRankingService challengeRankingService;
    private final ChallengeProgressService challengeProgressService;

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

    @GetMapping("/{challengeId}/summary")
    public ResponseEntity<ChallengeStatsDto.Summary> getSummary(@PathVariable Long challengeId) {
        return ResponseEntity.ok(challengeStatsService.getSummary(challengeId));
    }

    @GetMapping("/{challengeId}/ranking")
    public ResponseEntity<List<ChallengeStatsDto.Ranking>> getTop10Ranking(@PathVariable Long challengeId) {
        return ResponseEntity.ok(challengeRankingService.getTop10Ranking(challengeId));
    }

    @GetMapping("/{challengeId}/ranking/me")
    public ResponseEntity<ChallengeStatsDto.Ranking> getMyRank(
            @PathVariable Long challengeId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        return ResponseEntity.ok(
                challengeRankingService.getUserRank(challengeId, userPrincipal.getId())
        );
    }

    @GetMapping("/{challengeId}/progress/me")
    public ResponseEntity<ChallengeProgressDto> getMyProgress(
            @PathVariable Long challengeId,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        ChallengeProgressDto dto =
                challengeProgressService.getUserProgress(challengeId, userPrincipal.getId());
        return ResponseEntity.ok(dto);
    }
}
