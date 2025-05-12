package com.example.api.controller;

import com.example.api.config.UserPrincipal;
import com.example.api.dto.PostDto;
import com.example.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto.PostResponse> createPost(@RequestBody PostDto.PostRequest postRequest, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        PostDto.PostResponse postResponse = postService.createPost(postRequest, userPrincipal.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponse);
    }
}
