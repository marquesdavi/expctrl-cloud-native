package com.financial.feature.auth.service;

import com.financial.feature.auth.dto.LoginRequest;
import com.financial.feature.auth.dto.TokenResponse;
import com.financial.feature.auth.service.contract.AuthServiceContract;
import com.financial.feature.user.entity.User;
import com.financial.feature.user.repository.UserRepository;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequestScoped
@RequiredArgsConstructor
public class AuthService implements AuthServiceContract {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TokenResponse authenticate(LoginRequest loginRequest) {
        User user = userRepository.find("email", loginRequest.email())
                .firstResultOptional()
                .orElseThrow(() -> new NotAuthorizedException("Credenciais inválidas"));

        if (!BcryptUtil.matches(loginRequest.password(), user.passwordHash)) {
            throw new NotAuthorizedException("Credenciais inválidas");
        }

        String token = Jwt.issuer("expctrl")
                .upn(user.email)
                .groups(Set.of("user"))   // ajustar roles conforme seu modelo
                .sign();

        return new TokenResponse(token);
    }
}
