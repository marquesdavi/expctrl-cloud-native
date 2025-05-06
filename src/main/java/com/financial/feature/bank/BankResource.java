package com.financial.feature.bank;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.net.URI;
import java.util.List;

@Path("/banks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BankResource {

    @GET
    @Operation(summary = "List all banks")
    @APIResponse(responseCode = "200", description = "Banks retrieved")
    public List<BankDTO> list() {
        return Bank.listAll().stream()
                .map(peb -> {
                    var b = (Bank) peb;
                    return new BankDTO(b.id, b.name, b.code);
                })
                .toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get bank by ID")
    @APIResponse(responseCode = "200", description = "Bank found")
    @APIResponse(responseCode = "404", description = "Bank not found")
    public BankDTO get(@PathParam("id") Long id) {
        var peb = Bank.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var b = (Bank) peb;
        return new BankDTO(b.id, b.name, b.code);
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new bank")
    @APIResponse(responseCode = "201", description = "Bank created")
    public Response create(BankDTO dto) {
        var b = new Bank();
        b.name = dto.name();
        b.code = dto.code();
        b.persist();
        return Response.created(URI.create("/banks/" + b.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing bank")
    @APIResponse(responseCode = "200", description = "Bank updated")
    public BankDTO update(@PathParam("id") Long id, BankDTO dto) {
        var peb = Bank.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var b = (Bank) peb;
        b.name = dto.name();
        b.code = dto.code();
        return new BankDTO(b.id, b.name, b.code);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a bank")
    @APIResponse(responseCode = "204", description = "Bank deleted")
    public void delete(@PathParam("id") Long id) {
        Bank.findByIdOptional(id).orElseThrow(NotFoundException::new).delete();
    }
}



