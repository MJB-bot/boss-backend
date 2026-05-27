package com.bosszhipin.service.impl;

import com.bosszhipin.dto.request.LoginRequest;
import com.bosszhipin.dto.request.RegisterRequest;
import com.bosszhipin.dto.response.LoginResponse;
import com.bosszhipin.entity.Profile;
import com.bosszhipin.entity.User;
import com.bosszhipin.exception.BusinessException;
import com.bosszhipin.exception.ErrorCode;
import com.bosszhipin.repository.ProfileRepository;
import com.bosszhipin.repository.UserRepository;
import com.bosszhipin.security.JwtTokenProvider;
import com.bosszhipin.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${jwt.expiration}")
    private long expiration;

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new BusinessException(ErrorCode.USERNAME_EXISTS);
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .mobile(request.getPhone())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();
        user = userRepository.save(user);

        Profile profile = Profile.builder()
                .userId(user.getId())
                .build();
        profileRepository.save(profile);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (user.getStatus() == 0) {
            throw new BusinessException(ErrorCode.ACCOUNT_DISABLED);
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.PASSWORD_ERROR);
        }

        String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername());
        return LoginResponse.of(user.getId(), user.getUsername(), token, expiration);
    }
}
