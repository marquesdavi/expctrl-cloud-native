package com.financial.feature.auth.dto;

public record LoginRequest(
        String email,
        String password
) {}
