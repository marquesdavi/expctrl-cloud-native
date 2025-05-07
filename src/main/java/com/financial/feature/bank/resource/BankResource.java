package com.financial.feature.bank.resource;

import com.financial.feature.bank.dto.BankDTO;
import com.financial.feature.bank.service.contract.BankServiceContract;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.util.List;

@Path("/banks")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BankResource {
    private final BankServiceContract bankService;

    @GET
    @Operation(summary = "List all banks")
    @APIResponse(responseCode = "200", description = "Banks retrieved")
    public List<BankDTO> list() {
        return bankService.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get bank by ID")
    @APIResponse(responseCode = "200", description = "Bank found")
    @APIResponse(responseCode = "404", description = "Bank not found")
    public BankDTO get(@PathParam("id") Long id) {
        return bankService.get(id);
    }

    @POST
    @Operation(summary = "Create a new bank")
    @APIResponse(responseCode = "201", description = "Bank created")
    public Response create(BankDTO dto) {
        return bankService.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an existing bank")
    @APIResponse(responseCode = "200", description = "Bank updated")
    public BankDTO update(@PathParam("id") Long id, BankDTO dto) {
        return bankService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a bank")
    @APIResponse(responseCode = "204", description = "Bank deleted")
    public void delete(@PathParam("id") Long id) {
        bankService.delete(id);
    }
}



