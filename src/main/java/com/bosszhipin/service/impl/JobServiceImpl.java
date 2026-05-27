package com.bosszhipin.service.impl;

import com.bosszhipin.common.PageResult;
import com.bosszhipin.dto.request.JobSearchRequest;
import com.bosszhipin.dto.response.JobDetailResponse;
import com.bosszhipin.dto.response.JobResponse;
import com.bosszhipin.entity.Job;
import com.bosszhipin.exception.BusinessException;
import com.bosszhipin.exception.ErrorCode;
import com.bosszhipin.repository.ApplicationRepository;
import com.bosszhipin.repository.FavoriteRepository;
import com.bosszhipin.repository.JobRepository;
import com.bosszhipin.service.JobService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final FavoriteRepository favoriteRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public PageResult<JobResponse> listJobs(int page, int size) {
        Page<Job> jobPage = jobRepository.findByIsActiveOrderByCreatedAtDesc(
                1, PageRequest.of(page - 1, size));
        List<JobResponse> list = jobPage.getContent().stream()
                .map(JobResponse::fromEntity).toList();
        return PageResult.of(list, jobPage.getTotalElements(), page, size);
    }

    @Override
    public PageResult<JobResponse> searchJobs(JobSearchRequest req, int page, int size) {
        Specification<Job> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("isActive"), 1));

            if (StringUtils.hasText(req.getKeyword())) {
                String kw = "%" + req.getKeyword() + "%";
                predicates.add(cb.or(
                        cb.like(root.get("title"), kw),
                        cb.like(root.get("companyName"), kw)
                ));
            }
            if (StringUtils.hasText(req.getCity())) {
                predicates.add(cb.equal(root.get("city"), req.getCity()));
            }
            if (StringUtils.hasText(req.getDistrict())) {
                predicates.add(cb.equal(root.get("district"), req.getDistrict()));
            }
            if (StringUtils.hasText(req.getJobType())) {
                predicates.add(cb.equal(root.get("jobType"), req.getJobType()));
            }
            if (StringUtils.hasText(req.getExperienceRequired())) {
                predicates.add(cb.equal(root.get("experienceRequired"), req.getExperienceRequired()));
            }
            if (StringUtils.hasText(req.getEducationRequired())) {
                predicates.add(cb.equal(root.get("educationRequired"), req.getEducationRequired()));
            }
            if (req.getSalaryMin() != null) {
                predicates.add(cb.ge(root.get("salaryMax"), req.getSalaryMin()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Job> jobPage = jobRepository.findAll(spec,
                PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt")));
        List<JobResponse> list = jobPage.getContent().stream()
                .map(JobResponse::fromEntity).toList();
        return PageResult.of(list, jobPage.getTotalElements(), page, size);
    }

    @Override
    @Transactional
    public JobDetailResponse getJobDetail(Long jobId, Long userId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new BusinessException(ErrorCode.JOB_NOT_FOUND));

        // 浏览量 +1
        Integer vc = job.getViewCount();
        job.setViewCount((vc == null ? 0 : vc) + 1);
        jobRepository.save(job);

        boolean favorited = userId != null
                && favoriteRepository.existsByUserIdAndJobId(userId, jobId);
        boolean applied = userId != null
                && applicationRepository.existsByUserIdAndJobId(userId, jobId);

        return JobDetailResponse.fromEntity(job, favorited, applied);
    }

    @Override
    public List<JobResponse> getHotJobs(int limit) {
        return jobRepository.findHotJobs(PageRequest.of(0, limit))
                .stream().map(JobResponse::fromEntity).toList();
    }
}
