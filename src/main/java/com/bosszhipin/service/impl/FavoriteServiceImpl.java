package com.bosszhipin.service.impl;

import com.bosszhipin.common.PageResult;
import com.bosszhipin.dto.response.FavoriteResponse;
import com.bosszhipin.dto.response.JobResponse;
import com.bosszhipin.entity.Favorite;
import com.bosszhipin.entity.Job;
import com.bosszhipin.exception.BusinessException;
import com.bosszhipin.exception.ErrorCode;
import com.bosszhipin.repository.FavoriteRepository;
import com.bosszhipin.repository.JobRepository;
import com.bosszhipin.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final JobRepository jobRepository;

    @Override
    @Transactional
    public void addFavorite(Long userId, Long jobId) {
        if (!jobRepository.existsById(jobId)) {
            throw new BusinessException(ErrorCode.JOB_NOT_FOUND);
        }
        if (favoriteRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new BusinessException(ErrorCode.ALREADY_FAVORITED);
        }
        Favorite fav = Favorite.builder()
                .userId(userId)
                .jobId(jobId)
                .build();
        favoriteRepository.save(fav);
    }

    @Override
    @Transactional
    public void removeFavorite(Long userId, Long jobId) {
        if (!favoriteRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new BusinessException(ErrorCode.FAVORITE_NOT_FOUND);
        }
        favoriteRepository.deleteByUserIdAndJobId(userId, jobId);
    }

    @Override
    public PageResult<FavoriteResponse> listFavorites(Long userId, int page, int size) {
        Page<Favorite> favPage = favoriteRepository.findByUserIdOrderByCreatedAtDesc(
                userId, PageRequest.of(page - 1, size));

        List<FavoriteResponse> list = favPage.getContent().stream()
                .map(fav -> {
                    Job job = jobRepository.findById(fav.getJobId()).orElse(null);
                    JobResponse jobResp = job != null ? JobResponse.fromEntity(job) : null;
                    return FavoriteResponse.fromEntity(fav, jobResp);
                }).toList();

        return PageResult.of(list, favPage.getTotalElements(), page, size);
    }
}
