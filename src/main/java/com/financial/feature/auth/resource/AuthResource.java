package com.financial.feature.auth.resource;

import com.financial.feature.auth.dto.LoginRequest;
import com.financial.feature.auth.dto.TokenResponse;
import com.financial.feature.auth.service.contract.AuthServiceContract;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    AuthServiceContract authService;

    @POST
    @Operation(summary = "Autentica usuário e retorna JWT")
    @APIResponse(responseCode = "200", description = "Token JWT gerado")
    @APIResponse(responseCode = "401", description = "Credenciais inválidas")
    public TokenResponse authenticate(LoginRequest loginRequest) {
        return authService.authenticate(loginRequest);
    }
}
