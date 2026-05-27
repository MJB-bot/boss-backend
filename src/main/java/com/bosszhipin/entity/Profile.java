package com.bosszhipin.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "profile")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Column(name = "real_name", length = 50)
    private String realName;

    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;

    @Column
    @Builder.Default
    private Integer gender = 0;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(length = 50)
    private String city;

    @Column(name = "job_status", length = 30)
    private String jobStatus;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(name = "phone_visible")
    @Builder.Default
    private Integer phoneVisible = 1;

    @Column(name = "email_visible")
    @Builder.Default
    private Integer emailVisible = 1;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
