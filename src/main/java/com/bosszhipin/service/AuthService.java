package com.bosszhipin.service;

import com.bosszhipin.dto.request.LoginRequest;
import com.bosszhipin.dto.request.RegisterRequest;
import com.bosszhipin.dto.response.LoginResponse;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
