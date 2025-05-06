package com.financial.feature.payee.resource;

import com.financial.feature.payee.dto.PayeeDTO;
import com.financial.feature.payee.service.contract.PayeeServiceContract;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@Path("/payees")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PayeeResource {
    private final PayeeServiceContract payeeService;

    @GET
    @Operation(summary = "List all payees")
    @APIResponse(responseCode = "200", description = "Payees retrieved")
    public List<PayeeDTO> list() {
        return payeeService.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get payee by ID")
    @APIResponse(responseCode = "200", description = "Payee found")
    @APIResponse(responseCode = "404", description = "Payee not found")
    public PayeeDTO get(@PathParam("id") Long id) {
        return payeeService.get(id);
    }

    @POST
    @Operation(summary = "Create a new payee")
    @APIResponse(responseCode = "201", description = "Payee created")
    public Response create(PayeeDTO dto) {
        return payeeService.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an existing payee")
    @APIResponse(responseCode = "200", description = "Payee updated")
    public PayeeDTO update(@PathParam("id") Long id, PayeeDTO dto) {
        return payeeService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a payee")
    @APIResponse(responseCode = "204", description = "Payee deleted")
    public void delete(@PathParam("id") Long id) {
        payeeService.delete(id);
    }
}

