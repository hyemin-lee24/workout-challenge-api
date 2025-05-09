package com.example.api.service;

import com.example.api.dto.PostDto;
import com.example.api.mapper.UserRepository;
import com.example.api.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .email("test@user.com")
                .password("password123")
                .nickname("tester")
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(testUser);
    }

    @Test
    void createPost() {
        PostDto.CreateRequest request = PostDto.CreateRequest.builder()
                .user(testUser.getId())
                .title("서비스 게시글")
                .content("서비스 게시글 내용")
                .build();

        PostDto.PostResponse response = postService.createPost(request, testUser.getId());

        assertThat(response.getId()).isNotNull();
        assertThat(response.getTitle()).isEqualTo("서비스 게시글");
        assertThat(response.getUserId().getId()).isEqualTo(testUser.getId());
    }
}
