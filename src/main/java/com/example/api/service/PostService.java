package com.example.api.service;

import com.example.api.dto.PostDto;
import com.example.api.dto.WorkoutDataDto;
import com.example.api.mapper.PostRepository;
import com.example.api.mapper.UserRepository;
import com.example.api.mapper.WorkoutDataRepository;
import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.model.WorkoutData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public PostDto.PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        return PostDto.PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .createdAt(post.getCreatedAt())
                .user(post.getUser())
                .build();
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
}
