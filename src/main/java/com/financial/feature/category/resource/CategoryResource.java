package com.financial.feature.category.resource;

import com.financial.feature.category.dto.CategoryDTO;
import com.financial.feature.category.service.contract.CategoryServiceContract;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.util.List;


@Path("/categories")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {
    private final CategoryServiceContract categoryService;

    @GET
    @Operation(summary = "List all categories")
    @APIResponse(responseCode = "200", description = "Categories retrieved")
    public List<CategoryDTO> list() {
        return categoryService.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get category by ID")
    @APIResponse(responseCode = "200", description = "Category found")
    @APIResponse(responseCode = "404", description = "Category not found")
    public CategoryDTO get(@PathParam("id") Long id) {
        return categoryService.get(id);
    }

    @POST
    @Operation(summary = "Create a new category")
    @APIResponse(responseCode = "201", description = "Category created")
    public Response create(CategoryDTO dto) {
        return categoryService.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an existing category")
    @APIResponse(responseCode = "200", description = "Category updated")
    public CategoryDTO update(@PathParam("id") Long id, CategoryDTO dto) {
        return categoryService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a category")
    @APIResponse(responseCode = "204", description = "Category deleted")
    public void delete(@PathParam("id") Long id) {
        categoryService.delete(id);
    }
}

