package com.bosszhipin.controller;

import com.bosszhipin.common.ApiResponse;
import com.bosszhipin.common.PageResult;
import com.bosszhipin.dto.response.ApplicationResponse;
import com.bosszhipin.security.JwtTokenProvider;
import com.bosszhipin.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@Tag(name = "投递模块", description = "投递职位、投递记录")
public class ApplicationController {

    private final ApplicationService applicationService;
    private final JwtTokenProvider jwtTokenProvider;

    private Long getUserId(String authHeader) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        return jwtTokenProvider.getUserIdFromToken(token);
    }

    @PostMapping
    @Operation(summary = "投递职位")
    public ApiResponse<Void> apply(@RequestHeader("Authorization") String auth,
                                   @RequestBody Map<String, Object> body) {
        Long jobId = Long.valueOf(body.get("jobId").toString());
        String coverLetter = body.get("coverLetter") != null ? body.get("coverLetter").toString() : null;
        applicationService.apply(getUserId(auth), jobId, coverLetter);
        return ApiResponse.success();
    }

    @GetMapping
    @Operation(summary = "投递记录")
    public ApiResponse<PageResult<ApplicationResponse>> listApplications(
            @RequestHeader("Authorization") String auth,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(applicationService.listApplications(getUserId(auth), page, size));
    }
}
