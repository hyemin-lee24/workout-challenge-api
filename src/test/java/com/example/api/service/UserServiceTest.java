package com.example.api.service;

import com.example.api.dto.UserDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserServiceTest {

    @Autowired
    private UserService userService;

    private final String TEST_EMAIL = "test@example.com";
    private final String TEST_PASSWORD = "test_password";
    private final String TEST_NICKNAME = "testUser";

    private final String FAIL_DUMMY = "fail";

    @BeforeEach
    void registerUserTest() {
        // h2 @Table(name = "\"User\"")
        UserDto.SignUpRequest request = UserDto.SignUpRequest.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .nickname(TEST_NICKNAME)
                .build();

        UserDto.UserResponse response = userService.registerUser(request);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getEmail()).isEqualTo(TEST_EMAIL);
        assertThat(response.getNickname()).isEqualTo(TEST_NICKNAME);
    }

    @Test
    void loginTest() {
        UserDto.LoginRequest request = UserDto.LoginRequest.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD)
                .build();

        ResponseEntity<?> response = userService.login(request);

        assertThat(response).isNotNull();
    }

    @Test
    void loginFailTestForEmail() {
        UserDto.LoginRequest request = UserDto.LoginRequest.builder()
                .email(TEST_EMAIL + FAIL_DUMMY)
                .password(TEST_PASSWORD)
                .build();

        try {
             userService.login(request);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("This email does not exist.");
        }
    }

    @Test
    void loginFailTestForPassword() {
        UserDto.LoginRequest request = UserDto.LoginRequest.builder()
                .email(TEST_EMAIL)
                .password(TEST_PASSWORD + FAIL_DUMMY)
                .build();

        try {
            userService.login(request);
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Password does not match.");
        }
    }
}
