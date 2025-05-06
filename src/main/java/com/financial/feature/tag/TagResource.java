package com.financial.feature.tag;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.net.URI;
import java.util.List;

import com.financial.feature.user.User;


@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TagResource {

    @GET
    @Operation(summary = "List all tags")
    @APIResponse(responseCode = "200", description = "Tags retrieved")
    public List<TagDTO> list() {
        return Tag.listAll().stream()
                .map(peb -> {
                    var t = (Tag) peb;
                    return new TagDTO(t.id, t.user.id, t.name);
                })
                .toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get tag by ID")
    @APIResponse(responseCode = "200", description = "Tag found")
    @APIResponse(responseCode = "404", description = "Tag not found")
    public TagDTO get(@PathParam("id") Long id) {
        var peb = Tag.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var t = (Tag) peb;
        return new TagDTO(t.id, t.user.id, t.name);
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new tag")
    @APIResponse(responseCode = "201", description = "Tag created")
    public Response create(TagDTO dto) {
        var t = new Tag();
        t.user = (User) User.findById(dto.userId());
        t.name = dto.name();
        t.persist();
        return Response.created(URI.create("/tags/" + t.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing tag")
    @APIResponse(responseCode = "200", description = "Tag updated")
    public TagDTO update(@PathParam("id") Long id, TagDTO dto) {
        var peb = Tag.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var t = (Tag) peb;
        t.user = (User) User.findById(dto.userId());
        t.name = dto.name();
        return new TagDTO(t.id, t.user.id, t.name);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a tag")
    @APIResponse(responseCode = "204", description = "Tag deleted")
    public void delete(@PathParam("id") Long id) {
        Tag.findByIdOptional(id).orElseThrow(NotFoundException::new).delete();
    }
}

