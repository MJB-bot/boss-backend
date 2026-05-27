package com.bosszhipin.controller;

import com.bosszhipin.common.ApiResponse;
import com.bosszhipin.common.PageResult;
import com.bosszhipin.dto.request.JobSearchRequest;
import com.bosszhipin.dto.response.JobDetailResponse;
import com.bosszhipin.dto.response.JobResponse;
import com.bosszhipin.security.JwtTokenProvider;
import com.bosszhipin.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
@RequiredArgsConstructor
@Tag(name = "职位模块", description = "职位列表、搜索、筛选、详情、热门推荐")
public class JobController {

    private final JobService jobService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    @Operation(summary = "职位列表（分页）")
    public ApiResponse<PageResult<JobResponse>> listJobs(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(jobService.listJobs(page, size));
    }

    @GetMapping("/search")
    @Operation(summary = "搜索&筛选职位")
    public ApiResponse<PageResult<JobResponse>> searchJobs(
            JobSearchRequest request,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(jobService.searchJobs(request, page, size));
    }

    @GetMapping("/{id}")
    @Operation(summary = "职位详情")
    public ApiResponse<JobDetailResponse> getJobDetail(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        Long userId = null;
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            userId = jwtTokenProvider.getUserIdFromToken(authHeader.substring(7));
        }
        return ApiResponse.success(jobService.getJobDetail(id, userId));
    }

    @GetMapping("/hot")
    @Operation(summary = "热门推荐")
    public ApiResponse<List<JobResponse>> getHotJobs(
            @RequestParam(defaultValue = "10") int limit) {
        return ApiResponse.success(jobService.getHotJobs(limit));
    }
}
