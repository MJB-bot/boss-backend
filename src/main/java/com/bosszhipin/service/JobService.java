package com.bosszhipin.service;

import com.bosszhipin.common.PageResult;
import com.bosszhipin.dto.request.JobSearchRequest;
import com.bosszhipin.dto.response.JobDetailResponse;
import com.bosszhipin.dto.response.JobResponse;

import java.util.List;

public interface JobService {

    PageResult<JobResponse> listJobs(int page, int size);

    PageResult<JobResponse> searchJobs(JobSearchRequest request, int page, int size);

    JobDetailResponse getJobDetail(Long jobId, Long userId);

    List<JobResponse> getHotJobs(int limit);
}
