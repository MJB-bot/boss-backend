package com.bosszhipin.service.impl;

import com.bosszhipin.dto.request.ProfileUpdateRequest;
import com.bosszhipin.dto.response.ProfileResponse;
import com.bosszhipin.entity.Profile;
import com.bosszhipin.exception.BusinessException;
import com.bosszhipin.exception.ErrorCode;
import com.bosszhipin.repository.ProfileRepository;
import com.bosszhipin.service.ProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final ProfileRepository profileRepository;

    @Value("${file.upload.avatar-dir:uploads/avatars}")
    private String avatarDir;

    @Override
    public ProfileResponse getProfile(Long userId) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROFILE_NOT_FOUND));
        return ProfileResponse.fromEntity(profile);
    }

    @Override
    @Transactional
    public ProfileResponse updateProfile(Long userId, ProfileUpdateRequest request) {
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROFILE_NOT_FOUND));

        if (StringUtils.hasText(request.getRealName())) profile.setRealName(request.getRealName());
        if (request.getGender() != null) profile.setGender(request.getGender());
        if (request.getBirthDate() != null) profile.setBirthDate(request.getBirthDate());
        if (request.getCity() != null) profile.setCity(request.getCity());
        if (request.getJobStatus() != null) profile.setJobStatus(request.getJobStatus());
        if (request.getSummary() != null) profile.setSummary(request.getSummary());
        if (request.getPhoneVisible() != null) profile.setPhoneVisible(request.getPhoneVisible());
        if (request.getEmailVisible() != null) profile.setEmailVisible(request.getEmailVisible());

        profileRepository.save(profile);
        return ProfileResponse.fromEntity(profile);
    }

    @Override
    @Transactional
    public String uploadAvatar(Long userId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new BusinessException("文件不能为空");
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            throw new BusinessException("文件名无效");
        }

        String ext = originalFilename.substring(originalFilename.lastIndexOf('.'));
        if (!ext.matches("\\.(jpg|jpeg|png|gif|webp)")) {
            throw new BusinessException(ErrorCode.FILE_TYPE_UNSUPPORTED);
        }

        String newFilename = userId + "_" + UUID.randomUUID().toString().substring(0, 8) + ext;

        try {
            Path uploadPath = Paths.get(avatarDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            file.transferTo(new File(uploadPath + File.separator + newFilename));
        } catch (IOException e) {
            log.error("头像上传失败", e);
            throw new BusinessException(ErrorCode.FILE_UPLOAD_FAILED);
        }

        String avatarUrl = "/" + avatarDir + "/" + newFilename;
        Profile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROFILE_NOT_FOUND));
        profile.setAvatarUrl(avatarUrl);
        profileRepository.save(profile);

        return avatarUrl;
    }
}
