package com.example.api.controller;

import com.example.api.config.UserPrincipal;
import com.example.api.dto.StatisticsSummaryDto;
import com.example.api.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/summary")
    public ResponseEntity<StatisticsSummaryDto> getSummary(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        StatisticsSummaryDto summary = statisticsService.getUserSummary(userPrincipal.getId());
        return ResponseEntity.ok(summary);
    }
}
