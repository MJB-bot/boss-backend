package com.bosszhipin.dto.response;

import com.bosszhipin.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {

    private Long id;
    private Long userId;
    private String realName;
    private String avatarUrl;
    private Integer gender;
    private LocalDate birthDate;
    private String city;
    private String jobStatus;
    private String summary;
    private Integer phoneVisible;
    private Integer emailVisible;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static ProfileResponse fromEntity(Profile p) {
        return ProfileResponse.builder()
                .id(p.getId())
                .userId(p.getUserId())
                .realName(p.getRealName())
                .avatarUrl(p.getAvatarUrl())
                .gender(p.getGender())
                .birthDate(p.getBirthDate())
                .city(p.getCity())
                .jobStatus(p.getJobStatus())
                .summary(p.getSummary())
                .phoneVisible(p.getPhoneVisible())
                .emailVisible(p.getEmailVisible())
                .createdAt(p.getCreatedAt())
                .updatedAt(p.getUpdatedAt())
                .build();
    }
}
