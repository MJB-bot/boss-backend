package com.bosszhipin.service;

import com.bosszhipin.common.PageResult;
import com.bosszhipin.dto.response.ApplicationResponse;

public interface ApplicationService {

    void apply(Long userId, Long jobId, String coverLetter);

    PageResult<ApplicationResponse> listApplications(Long userId, int page, int size);
}
