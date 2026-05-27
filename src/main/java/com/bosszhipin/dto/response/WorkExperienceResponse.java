package com.bosszhipin.dto.response;

import com.bosszhipin.entity.WorkExperience;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperienceResponse {

    private Long id;
    private String companyName;
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Integer sortOrder;

    public static WorkExperienceResponse fromEntity(WorkExperience w) {
        return WorkExperienceResponse.builder()
                .id(w.getId())
                .companyName(w.getCompanyName())
                .position(w.getPosition())
                .startDate(w.getStartDate())
                .endDate(w.getEndDate())
                .description(w.getDescription())
                .sortOrder(w.getSortOrder())
                .build();
    }
}
