package com.example.api.service;

import com.example.api.dto.PostDto;
import com.example.api.mapper.UserRepository;
import com.example.api.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostServiceTest {
    @Autowired
    private PostService postService;

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    private String TEST_TITLE1 = "Title1";
    private String TEST_TITLE2 = "Title2";
    private String TEST_CONTENT1 = "content1";
    private String TEST_CONTENT2 = "content2";

    private long postId1;
    private long postId2;

    @BeforeEach
    void init() {
        setUpUser();
        setUpPost();
    }

    void setUpUser() {
        testUser = User.builder()
                .email("test@user.com")
                .password("password123")
                .nickname("tester")
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(testUser);
    }

    void setUpPost() {
        postId1 = createPost(TEST_TITLE1, TEST_CONTENT1);
        postId2 = createPost(TEST_TITLE2, TEST_CONTENT2);
    }

    long createPost(String title, String content) {
        PostDto.CreateRequest post = PostDto.CreateRequest.builder()
                .user(testUser.getId())
                .title(title)
                .content(content)
                .build();
        PostDto.PostResponse response = postService.createPost(post, testUser.getId());

        assertThat(response.getTitle()).isEqualTo(title);
        assertThat(response.getContent()).isEqualTo(content);

        return response.getId();
    }

    @Test
    void getAllPosts() {
        List<PostDto.PostResponse> posts = postService.getAllPosts();

        assertEquals(2, posts.size());

        List<String> titles = posts.stream()
                .map(PostDto.PostResponse::getTitle)
                .collect(Collectors.toList());

        assertTrue(titles.contains(TEST_TITLE1));
        assertTrue(titles.contains(TEST_TITLE2));
    }

    @Test
    void getPost() {
        assertEquals(postService.getPost(postId1).getContent(), TEST_CONTENT1);
        assertEquals(postService.getPost(postId2).getContent(), TEST_CONTENT2);
    }
}
