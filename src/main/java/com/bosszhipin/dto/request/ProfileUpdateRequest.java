package com.bosszhipin.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ProfileUpdateRequest {

    @Size(max = 50, message = "姓名最长50字")
    private String realName;

    private Integer gender;

    private LocalDate birthDate;

    @Size(max = 50, message = "城市最长50字")
    private String city;

    @Size(max = 30, message = "求职状态最长30字")
    private String jobStatus;

    @Size(max = 500, message = "个人简介最长500字")
    private String summary;

    private Integer phoneVisible;

    private Integer emailVisible;
}
