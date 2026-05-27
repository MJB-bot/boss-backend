package com.bosszhipin.dto.request;

import lombok.Data;

@Data
public class JobSearchRequest {

    private String keyword;
    private String city;
    private String district;
    private String jobType;
    private String experienceRequired;
    private String educationRequired;
    private Integer salaryMin;
    private Integer salaryMax;

    @Override
    public String toString() {
        return "JobSearchRequest{keyword='" + keyword + "', city='" + city + "'}";
    }
}
