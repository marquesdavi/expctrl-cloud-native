package com.financial.feature.auth.service.contract;

import com.financial.feature.auth.dto.LoginRequest;
import com.financial.feature.auth.dto.TokenResponse;

public interface AuthServiceContract {
    TokenResponse authenticate(LoginRequest loginRequest);
}
