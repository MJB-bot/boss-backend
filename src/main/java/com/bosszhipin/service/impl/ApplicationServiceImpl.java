package com.bosszhipin.service.impl;

import com.bosszhipin.common.PageResult;
import com.bosszhipin.dto.response.ApplicationResponse;
import com.bosszhipin.dto.response.JobResponse;
import com.bosszhipin.entity.Application;
import com.bosszhipin.entity.Job;
import com.bosszhipin.exception.BusinessException;
import com.bosszhipin.exception.ErrorCode;
import com.bosszhipin.repository.ApplicationRepository;
import com.bosszhipin.repository.JobRepository;
import com.bosszhipin.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    @Override
    @Transactional
    public void apply(Long userId, Long jobId, String coverLetter) {
        if (!jobRepository.existsById(jobId)) {
            throw new BusinessException(ErrorCode.JOB_NOT_FOUND);
        }
        if (applicationRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new BusinessException(ErrorCode.ALREADY_APPLIED);
        }
        Application app = Application.builder()
                .userId(userId)
                .jobId(jobId)
                .coverLetter(coverLetter)
                .build();
        applicationRepository.save(app);
    }

    @Override
    public PageResult<ApplicationResponse> listApplications(Long userId, int page, int size) {
        Page<Application> appPage = applicationRepository.findByUserIdOrderByCreatedAtDesc(
                userId, PageRequest.of(page - 1, size));

        List<ApplicationResponse> list = appPage.getContent().stream()
                .map(app -> {
                    Job job = jobRepository.findById(app.getJobId()).orElse(null);
                    JobResponse jobResp = job != null ? JobResponse.fromEntity(job) : null;
                    return ApplicationResponse.fromEntity(app, jobResp);
                }).toList();

        return PageResult.of(list, appPage.getTotalElements(), page, size);
    }
}
