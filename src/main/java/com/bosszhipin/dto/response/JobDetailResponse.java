package com.bosszhipin.dto.response;

import com.bosszhipin.entity.Job;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDetailResponse {

    private Long id;
    private String title;
    private String companyName;
    private String companyLogo;
    private Integer salaryMin;
    private Integer salaryMax;
    private String salaryType;
    private String city;
    private String district;
    private String address;
    private String experienceRequired;
    private String educationRequired;
    private String jobType;
    private String tags;
    private String description;
    private Integer headcount;
    private Integer viewCount;
    private Integer isHot;
    private Integer isActive;
    private Boolean isFavorited;
    private Boolean isApplied;

    public static JobDetailResponse fromEntity(Job job, boolean favorited, boolean applied) {
        return JobDetailResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .companyName(job.getCompanyName())
                .companyLogo(job.getCompanyLogo())
                .salaryMin(job.getSalaryMin())
                .salaryMax(job.getSalaryMax())
                .salaryType(job.getSalaryType())
                .city(job.getCity())
                .district(job.getDistrict())
                .address(job.getAddress())
                .experienceRequired(job.getExperienceRequired())
                .educationRequired(job.getEducationRequired())
                .jobType(job.getJobType())
                .tags(job.getTags())
                .description(job.getDescription())
                .headcount(job.getHeadcount())
                .viewCount(job.getViewCount())
                .isHot(job.getIsHot())
                .isActive(job.getIsActive())
                .isFavorited(favorited)
                .isApplied(applied)
                .build();
    }
}
