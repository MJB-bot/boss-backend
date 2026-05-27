package com.bosszhipin.dto.response;

import com.bosszhipin.entity.Application;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private String city;
    private Integer salaryMin;
    private Integer salaryMax;
    private String resumeStatus;
    private String coverLetter;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ApplicationResponse fromEntity(Application app, JobResponse job) {
        return ApplicationResponse.builder()
                .id(app.getId())
                .jobId(app.getJobId())
                .jobTitle(job != null ? job.getTitle() : null)
                .companyName(job != null ? job.getCompanyName() : null)
                .city(job != null ? job.getCity() : null)
                .salaryMin(job != null ? job.getSalaryMin() : null)
                .salaryMax(job != null ? job.getSalaryMax() : null)
                .resumeStatus(app.getResumeStatus())
                .coverLetter(app.getCoverLetter())
                .createdAt(app.getCreatedAt())
                .updatedAt(app.getUpdatedAt())
                .build();
    }
}
