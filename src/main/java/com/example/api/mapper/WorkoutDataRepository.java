package com.example.api.mapper;

import com.example.api.model.Post;
import com.example.api.model.WorkoutData;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface WorkoutDataRepository extends JpaRepository<WorkoutData, Long> {

    List<WorkoutData> findByUserId(Long userId);

    List<WorkoutData> findByPostId(Long postId);

    Optional<WorkoutData> findByPostIdAndUserId(Long postId, Long userId);

    Optional<WorkoutData> findByPostAndDeletedFalse(Post post);
}
