package com.financial.feature.transaction.resource;

import com.financial.feature.transaction.dto.TransactionDTO;
import com.financial.feature.transaction.service.contract.TransactionServiceContract;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;


@Path("/transactions")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {
    private final TransactionServiceContract transactionService;

    @GET
    @Operation(summary = "List all transactions")
    @APIResponse(responseCode = "200", description = "Transactions retrieved")
    public List<TransactionDTO> list() {
        return transactionService.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get transaction by ID")
    @APIResponse(responseCode = "200", description = "Transaction found")
    @APIResponse(responseCode = "404", description = "Transaction not found")
    public TransactionDTO get(@PathParam("id") Long id) {
        return transactionService.get(id);
    }

    @POST
    @Operation(summary = "Create a new transaction")
    @APIResponse(responseCode = "201", description = "Transaction created")
    public Response create(TransactionDTO dto) {
        return transactionService.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an existing transaction")
    @APIResponse(responseCode = "200", description = "Transaction updated")
    public TransactionDTO update(@PathParam("id") Long id, TransactionDTO dto) {
        return transactionService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a transaction")
    @APIResponse(responseCode = "204", description = "Transaction deleted")
    public void delete(@PathParam("id") Long id) {
        transactionService.delete(id);
    }
}

