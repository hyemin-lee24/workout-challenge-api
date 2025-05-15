package com.example.api.controller;

import com.example.api.config.UserPrincipal;
import com.example.api.dto.PostDto;
import com.example.api.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto.PostResponse> getPost(
            @PathVariable Long postId) {
        PostDto.PostResponse post = postService.getPost(postId);
        return ResponseEntity.ok(post);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto.PostResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody PostDto.PostRequest postRequest,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        PostDto.PostResponse updated = postService.updatePost(postId, postRequest, userPrincipal.getId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        postService.deletePost(postId, userPrincipal.getId());
        return ResponseEntity.noContent().build();
    }
}
