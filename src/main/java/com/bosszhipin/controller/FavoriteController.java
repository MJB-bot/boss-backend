package com.bosszhipin.controller;

import com.bosszhipin.common.ApiResponse;
import com.bosszhipin.common.PageResult;
import com.bosszhipin.dto.response.FavoriteResponse;
import com.bosszhipin.security.JwtTokenProvider;
import com.bosszhipin.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
@Tag(name = "收藏模块", description = "收藏/取消收藏/收藏列表")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final JwtTokenProvider jwtTokenProvider;

    private Long getUserId(String authHeader) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        return jwtTokenProvider.getUserIdFromToken(token);
    }

    @PostMapping("/{jobId}")
    @Operation(summary = "收藏职位")
    public ApiResponse<Void> addFavorite(@RequestHeader("Authorization") String auth,
                                         @PathVariable Long jobId) {
        favoriteService.addFavorite(getUserId(auth), jobId);
        return ApiResponse.success();
    }

    @DeleteMapping("/{jobId}")
    @Operation(summary = "取消收藏")
    public ApiResponse<Void> removeFavorite(@RequestHeader("Authorization") String auth,
                                            @PathVariable Long jobId) {
        favoriteService.removeFavorite(getUserId(auth), jobId);
        return ApiResponse.success();
    }

    @GetMapping
    @Operation(summary = "收藏列表")
    public ApiResponse<PageResult<FavoriteResponse>> listFavorites(
            @RequestHeader("Authorization") String auth,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ApiResponse.success(favoriteService.listFavorites(getUserId(auth), page, size));
    }
}
