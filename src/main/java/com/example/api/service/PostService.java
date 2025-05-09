package com.example.api.service;

import com.example.api.dto.PostDto;
import com.example.api.mapper.PostRepository;
import com.example.api.mapper.UserRepository;
import com.example.api.model.Post;
import com.example.api.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto.PostResponse createPost(PostDto.CreateRequest request, Long userId) {
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
        return PostDto.PostResponse.from(savedPost);
    }
}
