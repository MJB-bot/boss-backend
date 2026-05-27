package com.bosszhipin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class EducationRequest {

    @NotBlank(message = "学校名称不能为空")
    @Size(max = 100, message = "学校名称最长100字")
    private String schoolName;

    @Size(max = 50, message = "学历最长50字")
    private String degree;

    @Size(max = 100, message = "专业最长100字")
    private String major;

    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 500, message = "描述最长500字")
    private String description;

    private Integer sortOrder;
}
