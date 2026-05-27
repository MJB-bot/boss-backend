package com.bosszhipin.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "job")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Column(name = "company_logo", length = 255)
    private String companyLogo;

    @Column(name = "salary_min")
    private Integer salaryMin;

    @Column(name = "salary_max")
    private Integer salaryMax;

    @Column(name = "salary_type", length = 20)
    private String salaryType;

    @Column(length = 50)
    private String city;

    @Column(length = 100)
    private String district;

    @Column(length = 255)
    private String address;

    @Column(name = "experience_required", length = 50)
    private String experienceRequired;

    @Column(name = "education_required", length = 50)
    private String educationRequired;

    @Column(name = "job_type", length = 30)
    private String jobType;

    @Column(length = 500)
    private String tags;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    private Integer headcount = 1;

    @Column(name = "view_count")
    @Builder.Default
    private Integer viewCount = 0;

    @Column(name = "is_hot")
    @Builder.Default
    private Integer isHot = 0;

    @Column(name = "is_active")
    @Builder.Default
    private Integer isActive = 1;

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
