package com.financial.feature.budget.resource;

import com.financial.feature.budget.dto.BudgetDTO;
import com.financial.feature.budget.service.contract.BudgetServiceContract;
import io.quarkus.security.Authenticated;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;


@Authenticated
@Path("/budgets")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BudgetResource {
    private final BudgetServiceContract budgetService;

    @GET
    @Operation(summary = "List all budgets")
    @APIResponse(responseCode = "200", description = "Budgets retrieved")
    public List<BudgetDTO> list() {
        return budgetService.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get budget by ID")
    @APIResponse(responseCode = "200", description = "Budget found")
    @APIResponse(responseCode = "404", description = "Budget not found")
    public BudgetDTO get(@PathParam("id") Long id) {
        return budgetService.get(id);
    }

    @POST
    @Operation(summary = "Create a new budget")
    @APIResponse(responseCode = "201", description = "Budget created")
    public Response create(BudgetDTO dto) {
        return budgetService.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an existing budget")
    @APIResponse(responseCode = "200", description = "Budget updated")
    public BudgetDTO update(@PathParam("id") Long id, BudgetDTO dto) {
        return budgetService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a budget")
    @APIResponse(responseCode = "204", description = "Budget deleted")
    public void delete(@PathParam("id") Long id) {
        budgetService.delete(id);
    }
}
