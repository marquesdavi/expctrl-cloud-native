// src/main/java/com/financial/expctrl/resource/CategoryResource.java
package com.financial.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.net.URI;
import java.util.List;
import com.financial.dto.CategoryDTO;
import com.financial.entity.Category;
import com.financial.entity.User;


@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @GET
    @Operation(summary = "List all categories")
    @APIResponse(responseCode = "200", description = "Categories retrieved")
    public List<CategoryDTO> list() {
        return Category.listAll().stream()
                .map(peb -> {
                    var c = (Category) peb;
                    return new CategoryDTO(
                            c.id,
                            c.user.id,
                            c.name,
                            c.type,
                            c.parentCategory != null ? c.parentCategory.id : null
                    );
                })
                .toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get category by ID")
    @APIResponse(responseCode = "200", description = "Category found")
    @APIResponse(responseCode = "404", description = "Category not found")
    public CategoryDTO get(@PathParam("id") Long id) {
        var peb = Category.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var c = (Category) peb;
        return new CategoryDTO(
                c.id,
                c.user.id,
                c.name,
                c.type,
                c.parentCategory != null ? c.parentCategory.id : null
        );
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new category")
    @APIResponse(responseCode = "201", description = "Category created")
    public Response create(CategoryDTO dto) {
        var c = new Category();
        c.user           = (User) User.findById(dto.userId());
        c.name           = dto.name();
        c.type           = dto.type();
        c.parentCategory = dto.parentCategoryId() != null
                ? (Category) Category.findById(dto.parentCategoryId())
                : null;
        c.persist();
        return Response.created(URI.create("/categories/" + c.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing category")
    @APIResponse(responseCode = "200", description = "Category updated")
    public CategoryDTO update(@PathParam("id") Long id, CategoryDTO dto) {
        var peb = Category.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var c = (Category) peb;
        c.user           = (User) User.findById(dto.userId());
        c.name           = dto.name();
        c.type           = dto.type();
        c.parentCategory = dto.parentCategoryId() != null
                ? (Category) Category.findById(dto.parentCategoryId())
                : null;
        return new CategoryDTO(
                c.id,
                c.user.id,
                c.name,
                c.type,
                c.parentCategory != null ? c.parentCategory.id : null
        );
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a category")
    @APIResponse(responseCode = "204", description = "Category deleted")
    public void delete(@PathParam("id") Long id) {
        Category.findByIdOptional(id).orElseThrow(NotFoundException::new).delete();
    }
}

