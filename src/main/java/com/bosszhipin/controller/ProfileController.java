package com.bosszhipin.controller;

import com.bosszhipin.common.ApiResponse;
import com.bosszhipin.dto.request.ProfileUpdateRequest;
import com.bosszhipin.dto.response.ProfileResponse;
import com.bosszhipin.security.JwtTokenProvider;
import com.bosszhipin.service.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
@Tag(name = "个人资料", description = "获取/编辑资料、上传头像")
public class ProfileController {

    private final ProfileService profileService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping
    @Operation(summary = "获取个人资料")
    public ApiResponse<ProfileResponse> getProfile(@RequestHeader("Authorization") String authHeader) {
        Long userId = getUserId(authHeader);
        return ApiResponse.success(profileService.getProfile(userId));
    }

    @PutMapping
    @Operation(summary = "编辑个人资料")
    public ApiResponse<ProfileResponse> updateProfile(@RequestHeader("Authorization") String authHeader,
                                                       @Valid @RequestBody ProfileUpdateRequest request) {
        Long userId = getUserId(authHeader);
        return ApiResponse.success(profileService.updateProfile(userId, request));
    }

    @PostMapping("/avatar")
    @Operation(summary = "上传头像")
    public ApiResponse<String> uploadAvatar(@RequestHeader("Authorization") String authHeader,
                                            @RequestParam("file") MultipartFile file) {
        Long userId = getUserId(authHeader);
        return ApiResponse.success(profileService.uploadAvatar(userId, file));
    }

    private Long getUserId(String authHeader) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        return jwtTokenProvider.getUserIdFromToken(token);
    }
}
