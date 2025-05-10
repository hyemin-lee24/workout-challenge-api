package com.example.api.service;

import com.example.api.dto.PostDto;
import com.example.api.mapper.PostRepository;
import com.example.api.mapper.UserRepository;
import com.example.api.model.Post;
import com.example.api.model.User;
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
                .authorNickname(post.getUser().getNickname())
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
                        .authorNickname(post.getUser().getNickname())
                        .build())
                .collect(Collectors.toList());
    }
}
