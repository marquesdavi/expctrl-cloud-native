package com.financial.feature.budget;

import com.financial.feature.category.Category;
import com.financial.feature.user.User;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.net.URI;
import java.util.List;


@Path("/budgets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BudgetResource {

    @GET
    @Operation(summary = "List all budgets")
    @APIResponse(responseCode = "200", description = "Budgets retrieved")
    public List<BudgetDTO> list() {
        return Budget.listAll().stream()
                .map(peb -> {
                    var b = (Budget) peb;
                    return new BudgetDTO(
                            b.id,
                            b.user.id,
                            b.category.id,
                            b.periodStart,
                            b.periodEnd,
                            b.amount
                    );
                })
                .toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get budget by ID")
    @APIResponse(responseCode = "200", description = "Budget found")
    @APIResponse(responseCode = "404", description = "Budget not found")
    public BudgetDTO get(@PathParam("id") Long id) {
        var peb = Budget.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var b = (Budget) peb;
        return new BudgetDTO(
                b.id,
                b.user.id,
                b.category.id,
                b.periodStart,
                b.periodEnd,
                b.amount
        );
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new budget")
    @APIResponse(responseCode = "201", description = "Budget created")
    public Response create(BudgetDTO dto) {
        var b = new Budget();
        b.user        = (User) User.findById(dto.userId());
        b.category    = (Category) Category.findById(dto.categoryId());
        b.periodStart = dto.periodStart();
        b.periodEnd   = dto.periodEnd();
        b.amount      = dto.amount();
        b.persist();
        return(Response.created(URI.create("/budgets/" + b.id)).build());
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing budget")
    @APIResponse(responseCode = "200", description = "Budget updated")
    public BudgetDTO update(@PathParam("id") Long id, BudgetDTO dto) {
        var peb = Budget.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var b = (Budget) peb;
        b.user        = (User) User.findById(dto.userId());
        b.category    = (Category) Category.findById(dto.categoryId());
        b.periodStart = dto.periodStart();
        b.periodEnd   = dto.periodEnd();
        b.amount      = dto.amount();
        return new BudgetDTO(
                b.id,
                b.user.id,
                b.category.id,
                b.periodStart,
                b.periodEnd,
                b.amount
        );
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a budget")
    @APIResponse(responseCode = "204", description = "Budget deleted")
    public void delete(@PathParam("id") Long id) {
        Budget.findByIdOptional(id).orElseThrow(NotFoundException::new).delete();
    }
}
