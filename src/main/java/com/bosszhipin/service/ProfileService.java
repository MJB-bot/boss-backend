package com.bosszhipin.service;

import com.bosszhipin.dto.request.ProfileUpdateRequest;
import com.bosszhipin.dto.response.ProfileResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProfileService {

    ProfileResponse getProfile(Long userId);

    ProfileResponse updateProfile(Long userId, ProfileUpdateRequest request);

    String uploadAvatar(Long userId, MultipartFile file);
}
