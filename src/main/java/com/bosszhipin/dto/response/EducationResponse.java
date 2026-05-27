package com.bosszhipin.dto.response;

import com.bosszhipin.entity.Education;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationResponse {

    private Long id;
    private String schoolName;
    private String degree;
    private String major;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Integer sortOrder;

    public static EducationResponse fromEntity(Education e) {
        return EducationResponse.builder()
                .id(e.getId())
                .schoolName(e.getSchoolName())
                .degree(e.getDegree())
                .major(e.getMajor())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .description(e.getDescription())
                .sortOrder(e.getSortOrder())
                .build();
    }
}
