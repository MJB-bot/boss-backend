package com.bosszhipin.controller;

import com.bosszhipin.common.ApiResponse;
import com.bosszhipin.dto.request.EducationRequest;
import com.bosszhipin.dto.request.WorkExperienceRequest;
import com.bosszhipin.dto.response.EducationResponse;
import com.bosszhipin.dto.response.WorkExperienceResponse;
import com.bosszhipin.security.JwtTokenProvider;
import com.bosszhipin.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resume")
@RequiredArgsConstructor
@Tag(name = "简历模块", description = "教育经历、工作经历 CRUD")
public class ResumeController {

    private final ResumeService resumeService;
    private final JwtTokenProvider jwtTokenProvider;

    private Long getUserId(String authHeader) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        return jwtTokenProvider.getUserIdFromToken(token);
    }

    // ========== 教育经历 ==========

    @GetMapping("/education")
    @Operation(summary = "获取教育经历列表")
    public ApiResponse<List<EducationResponse>> listEducation(@RequestHeader("Authorization") String auth) {
        return ApiResponse.success(resumeService.listEducation(getUserId(auth)));
    }

    @PostMapping("/education")
    @Operation(summary = "新增教育经历")
    public ApiResponse<EducationResponse> addEducation(@RequestHeader("Authorization") String auth,
                                                        @Valid @RequestBody EducationRequest request) {
        return ApiResponse.success(resumeService.addEducation(getUserId(auth), request));
    }

    @PutMapping("/education/{id}")
    @Operation(summary = "编辑教育经历")
    public ApiResponse<EducationResponse> updateEducation(@RequestHeader("Authorization") String auth,
                                                          @PathVariable Long id,
                                                          @Valid @RequestBody EducationRequest request) {
        return ApiResponse.success(resumeService.updateEducation(getUserId(auth), id, request));
    }

    @DeleteMapping("/education/{id}")
    @Operation(summary = "删除教育经历")
    public ApiResponse<Void> deleteEducation(@RequestHeader("Authorization") String auth,
                                             @PathVariable Long id) {
        resumeService.deleteEducation(getUserId(auth), id);
        return ApiResponse.success();
    }

    // ========== 工作经历 ==========

    @GetMapping("/work-experience")
    @Operation(summary = "获取工作经历列表")
    public ApiResponse<List<WorkExperienceResponse>> listWorkExperience(@RequestHeader("Authorization") String auth) {
        return ApiResponse.success(resumeService.listWorkExperience(getUserId(auth)));
    }

    @PostMapping("/work-experience")
    @Operation(summary = "新增工作经历")
    public ApiResponse<WorkExperienceResponse> addWorkExperience(@RequestHeader("Authorization") String auth,
                                                                  @Valid @RequestBody WorkExperienceRequest request) {
        return ApiResponse.success(resumeService.addWorkExperience(getUserId(auth), request));
    }

    @PutMapping("/work-experience/{id}")
    @Operation(summary = "编辑工作经历")
    public ApiResponse<WorkExperienceResponse> updateWorkExperience(@RequestHeader("Authorization") String auth,
                                                                     @PathVariable Long id,
                                                                     @Valid @RequestBody WorkExperienceRequest request) {
        return ApiResponse.success(resumeService.updateWorkExperience(getUserId(auth), id, request));
    }

    @DeleteMapping("/work-experience/{id}")
    @Operation(summary = "删除工作经历")
    public ApiResponse<Void> deleteWorkExperience(@RequestHeader("Authorization") String auth,
                                                  @PathVariable Long id) {
        resumeService.deleteWorkExperience(getUserId(auth), id);
        return ApiResponse.success();
    }
}
