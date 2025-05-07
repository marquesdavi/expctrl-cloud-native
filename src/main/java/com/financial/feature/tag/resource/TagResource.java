package com.financial.feature.tag.resource;

import com.financial.feature.tag.dto.TagDTO;
import com.financial.feature.tag.service.contract.TagServiceContract;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.util.List;


@Path("/tags")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {
    private final TagServiceContract tagService;

    @GET
    @Operation(summary = "List all tags")
    @APIResponse(responseCode = "200", description = "Tags retrieved")
    public List<TagDTO> list() {
        return tagService.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get tag by ID")
    @APIResponse(responseCode = "200", description = "Tag found")
    @APIResponse(responseCode = "404", description = "Tag not found")
    public TagDTO get(@PathParam("id") Long id) {
        return tagService.get(id);
    }

    @POST
    @Operation(summary = "Create a new tag")
    @APIResponse(responseCode = "201", description = "Tag created")
    public Response create(TagDTO dto) {
        return tagService.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update an existing tag")
    @APIResponse(responseCode = "200", description = "Tag updated")
    public TagDTO update(@PathParam("id") Long id, TagDTO dto) {
        return tagService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a tag")
    @APIResponse(responseCode = "204", description = "Tag deleted")
    public void delete(@PathParam("id") Long id) {
        tagService.delete(id);
    }
}

