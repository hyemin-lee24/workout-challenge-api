package com.example.api.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ChallengeEntry")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChallengeEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Challenge 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id", nullable = false)
    private Challenge challenge;

    // User 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "total_distance_km", nullable = false)
    private float totalDistanceKm = 0f;

    @Column
    private Integer ranking;
}
