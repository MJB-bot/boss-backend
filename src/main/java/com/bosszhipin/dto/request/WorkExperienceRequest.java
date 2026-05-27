package com.bosszhipin.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class WorkExperienceRequest {

    @NotBlank(message = "公司名称不能为空")
    @Size(max = 100, message = "公司名称最长100字")
    private String companyName;

    @Size(max = 100, message = "职位最长100字")
    private String position;

    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 500, message = "描述最长500字")
    private String description;

    private Integer sortOrder;
}
