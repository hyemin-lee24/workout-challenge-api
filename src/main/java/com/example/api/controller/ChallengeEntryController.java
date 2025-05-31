package com.example.api.controller;

import com.example.api.config.UserPrincipal;
import com.example.api.dto.ChallengeEntryDto;
import com.example.api.service.ChallengeEntryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/challenge-entries")
public class ChallengeEntryController {

    private final ChallengeEntryService challengeEntryService;

    @PostMapping
    public ResponseEntity<ChallengeEntryDto.Response> joinChallenge(
            @RequestBody ChallengeEntryDto.Request request,
            @AuthenticationPrincipal UserPrincipal userPrincipal
    ) {
        ChallengeEntryDto.Response response =
                challengeEntryService.joinChallenge(userPrincipal.getId(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
