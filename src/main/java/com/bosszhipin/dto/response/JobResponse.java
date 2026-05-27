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
public class JobResponse {

    private Long id;
    private String title;
    private String companyName;
    private String companyLogo;
    private Integer salaryMin;
    private Integer salaryMax;
    private String salaryType;
    private String city;
    private String district;
    private String experienceRequired;
    private String educationRequired;
    private String jobType;
    private String tags;
    private Integer viewCount;
    private Integer isHot;

    public static JobResponse fromEntity(Job job) {
        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .companyName(job.getCompanyName())
                .companyLogo(job.getCompanyLogo())
                .salaryMin(job.getSalaryMin())
                .salaryMax(job.getSalaryMax())
                .salaryType(job.getSalaryType())
                .city(job.getCity())
                .district(job.getDistrict())
                .experienceRequired(job.getExperienceRequired())
                .educationRequired(job.getEducationRequired())
                .jobType(job.getJobType())
                .tags(job.getTags())
                .viewCount(job.getViewCount())
                .isHot(job.getIsHot())
                .build();
    }
}
