package com.bosszhipin.service.impl;

import com.bosszhipin.dto.request.EducationRequest;
import com.bosszhipin.dto.request.WorkExperienceRequest;
import com.bosszhipin.dto.response.EducationResponse;
import com.bosszhipin.dto.response.WorkExperienceResponse;
import com.bosszhipin.entity.Education;
import com.bosszhipin.entity.WorkExperience;
import com.bosszhipin.exception.BusinessException;
import com.bosszhipin.exception.ErrorCode;
import com.bosszhipin.repository.EducationRepository;
import com.bosszhipin.repository.WorkExperienceRepository;
import com.bosszhipin.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final EducationRepository educationRepository;
    private final WorkExperienceRepository workExperienceRepository;

    // ==================== 教育经历 ====================

    @Override
    public List<EducationResponse> listEducation(Long userId) {
        return educationRepository.findByUserIdOrderBySortOrderAscStartDateDesc(userId)
                .stream().map(EducationResponse::fromEntity).toList();
    }

    @Override
    @Transactional
    public EducationResponse addEducation(Long userId, EducationRequest request) {
        Education edu = Education.builder()
                .userId(userId)
                .schoolName(request.getSchoolName())
                .degree(request.getDegree())
                .major(request.getMajor())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .description(request.getDescription())
                .sortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0)
                .build();
        edu = educationRepository.save(edu);
        return EducationResponse.fromEntity(edu);
    }

    @Override
    @Transactional
    public EducationResponse updateEducation(Long userId, Long eduId, EducationRequest request) {
        Education edu = educationRepository.findById(eduId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EDUCATION_NOT_FOUND));
        if (!edu.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        edu.setSchoolName(request.getSchoolName());
        edu.setDegree(request.getDegree());
        edu.setMajor(request.getMajor());
        edu.setStartDate(request.getStartDate());
        edu.setEndDate(request.getEndDate());
        edu.setDescription(request.getDescription());
        if (request.getSortOrder() != null) edu.setSortOrder(request.getSortOrder());
        educationRepository.save(edu);
        return EducationResponse.fromEntity(edu);
    }

    @Override
    @Transactional
    public void deleteEducation(Long userId, Long eduId) {
        Education edu = educationRepository.findById(eduId)
                .orElseThrow(() -> new BusinessException(ErrorCode.EDUCATION_NOT_FOUND));
        if (!edu.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        educationRepository.delete(edu);
    }

    // ==================== 工作经历 ====================

    @Override
    public List<WorkExperienceResponse> listWorkExperience(Long userId) {
        return workExperienceRepository.findByUserIdOrderBySortOrderAscStartDateDesc(userId)
                .stream().map(WorkExperienceResponse::fromEntity).toList();
    }

    @Override
    @Transactional
    public WorkExperienceResponse addWorkExperience(Long userId, WorkExperienceRequest request) {
        WorkExperience work = WorkExperience.builder()
                .userId(userId)
                .companyName(request.getCompanyName())
                .position(request.getPosition())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .description(request.getDescription())
                .sortOrder(request.getSortOrder() != null ? request.getSortOrder() : 0)
                .build();
        work = workExperienceRepository.save(work);
        return WorkExperienceResponse.fromEntity(work);
    }

    @Override
    @Transactional
    public WorkExperienceResponse updateWorkExperience(Long userId, Long workId, WorkExperienceRequest request) {
        WorkExperience work = workExperienceRepository.findById(workId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORK_EXP_NOT_FOUND));
        if (!work.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        work.setCompanyName(request.getCompanyName());
        work.setPosition(request.getPosition());
        work.setStartDate(request.getStartDate());
        work.setEndDate(request.getEndDate());
        work.setDescription(request.getDescription());
        if (request.getSortOrder() != null) work.setSortOrder(request.getSortOrder());
        workExperienceRepository.save(work);
        return WorkExperienceResponse.fromEntity(work);
    }

    @Override
    @Transactional
    public void deleteWorkExperience(Long userId, Long workId) {
        WorkExperience work = workExperienceRepository.findById(workId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORK_EXP_NOT_FOUND));
        if (!work.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
        workExperienceRepository.delete(work);
    }
}
