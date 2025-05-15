package com.example.api.service;

import com.example.api.dto.PostDto;
import com.example.api.dto.WorkoutDataDto;
import com.example.api.mapper.PostRepository;
import com.example.api.mapper.UserRepository;
import com.example.api.mapper.WorkoutDataRepository;
import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.model.WorkoutData;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final WorkoutDataRepository workoutDataRepository;

    public PostDto.PostResponse createPost(PostDto.PostRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Post post = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .createdAt(LocalDateTime.now())
                .build();
        Post savedPost = postRepository.save(post);

        WorkoutDataDto.WorkoutDataRequest workoutRequest = request.getWorkoutData();
        WorkoutData workoutData = WorkoutData.builder()
                .user(user)
                .post(savedPost)
                .distanceKm(workoutRequest.getDistanceKm())
                .durationSeconds(workoutRequest.getDurationSeconds())
                .avgSpeed(workoutRequest.getAvgSpeed())
                .caloriesBurned(workoutRequest.getCaloriesBurned())
                .locationData(workoutRequest.getLocationData()) // JSON string
                .build();
        workoutDataRepository.save(workoutData);

        return PostDto.PostResponse.from(savedPost, workoutData);
    }

    @Transactional(readOnly = true)
    public PostDto.PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        WorkoutData workoutData = workoutDataRepository.findByPostAndDeletedFalse(post)
                .orElse(null);

        return PostDto.PostResponse.from(post, workoutData);
    }

    public List<PostDto.PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(post -> PostDto.PostResponse.builder()
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .imageUrl(post.getImageUrl())
                        .createdAt(post.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public PostDto.PostResponse updatePost(Long postId, PostDto.PostRequest request, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not found"));

        if (!post.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("No permission to edit this post.");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setImageUrl(request.getImageUrl());

        return PostDto.PostResponse.from(post, post.getWorkoutData());
    }

    @Transactional
    public void deletePost(Long postId, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (!post.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not the owner of this post");
        }

        workoutDataRepository.findByPostAndDeletedFalse(post).ifPresent(WorkoutData::softDelete);

        postRepository.delete(post);
    }
}
