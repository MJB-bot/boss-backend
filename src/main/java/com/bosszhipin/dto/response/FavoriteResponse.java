package com.bosszhipin.dto.response;

import com.bosszhipin.entity.Favorite;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteResponse {

    private Long id;
    private Long jobId;
    private String jobTitle;
    private String companyName;
    private String city;
    private Integer salaryMin;
    private Integer salaryMax;
    private LocalDateTime createdAt;

    public static FavoriteResponse fromEntity(Favorite fav, JobResponse job) {
        return FavoriteResponse.builder()
                .id(fav.getId())
                .jobId(fav.getJobId())
                .jobTitle(job != null ? job.getTitle() : null)
                .companyName(job != null ? job.getCompanyName() : null)
                .city(job != null ? job.getCity() : null)
                .salaryMin(job != null ? job.getSalaryMin() : null)
                .salaryMax(job != null ? job.getSalaryMax() : null)
                .createdAt(fav.getCreatedAt())
                .build();
    }
}
