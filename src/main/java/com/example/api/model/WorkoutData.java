package com.example.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "WorkoutData")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkoutData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Post 연관관계
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // User 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "distance_km", nullable = false)
    private float distanceKm;

    @Column(name = "duration_seconds", nullable = false)
    private int durationSeconds;

    @Column(name = "avg_speed", nullable = false)
    private float avgSpeed;

    @Column(name = "calories_burned", nullable = false)
    private float caloriesBurned;

    @Column(name = "location_data", columnDefinition = "TEXT")
    private String locationData; // JSON을 TEXT로 변경
}
