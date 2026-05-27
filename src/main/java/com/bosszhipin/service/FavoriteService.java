package com.bosszhipin.service;

import com.bosszhipin.common.PageResult;
import com.bosszhipin.dto.response.FavoriteResponse;

public interface FavoriteService {

    void addFavorite(Long userId, Long jobId);

    void removeFavorite(Long userId, Long jobId);

    PageResult<FavoriteResponse> listFavorites(Long userId, int page, int size);
}
