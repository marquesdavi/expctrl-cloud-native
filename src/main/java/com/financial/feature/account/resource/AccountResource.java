package com.financial.feature.account.resource;

import com.financial.feature.account.dto.AccountDTO;
import com.financial.feature.account.service.contract.AccountServiceContract;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;


@Authenticated
@Path("/accounts")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {
    private final AccountServiceContract accountService;

    @GET
    @Operation(summary = "List all accounts")
    @APIResponse(responseCode = "200", description = "Accounts retrieved")
    public List<AccountDTO> list() {
        return accountService.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get account by ID")
    @APIResponse(responseCode = "200", description = "Account found")
    @APIResponse(responseCode = "404", description = "Account not found")
    public AccountDTO get(@PathParam("id") Long id) {
        return accountService.get(id);
    }

    @POST
    @Operation(summary = "Create a new account")
    @APIResponse(responseCode = "201", description = "Account created")
    public Response create(AccountDTO dto) {
        return accountService.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an existing account")
    @APIResponse(responseCode = "200", description = "Account updated")
    public AccountDTO update(@PathParam("id") Long id, AccountDTO dto) {
        return accountService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete an account")
    @APIResponse(responseCode = "204", description = "Account deleted")
    public void delete(@PathParam("id") Long id) {
        accountService.delete(id);
    }
}

