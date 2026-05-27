package com.bosszhipin.service;

import com.bosszhipin.dto.request.EducationRequest;
import com.bosszhipin.dto.request.WorkExperienceRequest;
import com.bosszhipin.dto.response.EducationResponse;
import com.bosszhipin.dto.response.WorkExperienceResponse;

import java.util.List;

public interface ResumeService {

    // 教育经历
    List<EducationResponse> listEducation(Long userId);
    EducationResponse addEducation(Long userId, EducationRequest request);
    EducationResponse updateEducation(Long userId, Long eduId, EducationRequest request);
    void deleteEducation(Long userId, Long eduId);

    // 工作经历
    List<WorkExperienceResponse> listWorkExperience(Long userId);
    WorkExperienceResponse addWorkExperience(Long userId, WorkExperienceRequest request);
    WorkExperienceResponse updateWorkExperience(Long userId, Long workId, WorkExperienceRequest request);
    void deleteWorkExperience(Long userId, Long workId);
}
