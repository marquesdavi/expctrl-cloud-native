package com.financial.resource;

import com.financial.dto.AccountDTO;
import com.financial.entity.Account;
import com.financial.entity.Bank;
import com.financial.entity.User;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.net.URI;
import java.util.List;

@Path("/accounts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    @GET
    @Operation(summary = "List all accounts")
    @APIResponse(responseCode = "200", description = "Accounts retrieved")
    public List<AccountDTO> list() {
        return Account.listAll().stream()
                .map(peb -> {
                    var a = (Account) peb;
                    return new AccountDTO(
                            a.id,
                            a.bank.id,
                            a.user.id,
                            a.accountNumber,
                            a.branch,
                            a.currency
                    );
                })
                .toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get account by ID")
    @APIResponse(responseCode = "200", description = "Account found")
    @APIResponse(responseCode = "404", description = "Account not found")
    public AccountDTO get(@PathParam("id") Long id) {
        var peb = Account.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
        var a = (Account) peb;
        return new AccountDTO(
                a.id,
                a.bank.id,
                a.user.id,
                a.accountNumber,
                a.branch,
                a.currency
        );
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new account")
    @APIResponse(responseCode = "201", description = "Account created")
    public Response create(AccountDTO dto) {
        var a = new Account();
        a.bank = (Bank) Bank.findById(dto.bankId());
        a.user = (User) User.findById(dto.userId());
        a.accountNumber = dto.accountNumber();
        a.branch        = dto.branch();
        a.currency      = dto.currency();
        a.persist();
        return Response.created(URI.create("/accounts/" + a.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing account")
    @APIResponse(responseCode = "200", description = "Account updated")
    public AccountDTO update(@PathParam("id") Long id, AccountDTO dto) {
        var peb = Account.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
        var a = (Account) peb;
        a.bank          = (Bank) Bank.findById(dto.bankId());
        a.user          = (User) User.findById(dto.userId());
        a.accountNumber = dto.accountNumber();
        a.branch        = dto.branch();
        a.currency      = dto.currency();
        return new AccountDTO(
                a.id,
                a.bank.id,
                a.user.id,
                a.accountNumber,
                a.branch,
                a.currency
        );
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete an account")
    @APIResponse(responseCode = "204", description = "Account deleted")
    public void delete(@PathParam("id") Long id) {
        Account.findByIdOptional(id)
                .orElseThrow(NotFoundException::new)
                .delete();
    }
}

