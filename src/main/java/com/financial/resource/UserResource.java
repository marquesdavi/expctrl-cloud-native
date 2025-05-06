package com.financial.resource;


import com.financial.dto.UserDTO;
import com.financial.entity.User;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.net.URI;
import java.time.Instant;
import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @GET
    @Operation(summary = "List all users")
    @APIResponse(responseCode = "200", description = "Users retrieved")
    public List<UserDTO> list() {
        return User.listAll().stream()
                .map(peb -> {
                    var u = (User) peb;
                    return new UserDTO(u.id, u.name, u.email, u.createdAt);
                })
                .toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get user by ID")
    @APIResponse(responseCode = "200", description = "User found")
    @APIResponse(responseCode = "404", description = "User not found")
    public UserDTO get(@PathParam("id") Long id) {
        var peb = User.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var u = (User) peb;
        return new UserDTO(u.id, u.name, u.email, u.createdAt);
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new user")
    @APIResponse(responseCode = "201", description = "User created")
    public Response create(UserDTO dto) {
        var u = new User();
        u.name = dto.name();
        u.email = dto.email();
        u.createdAt = Instant.now();
        u.persist();
        return Response.created(URI.create("/users/" + u.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing user")
    @APIResponse(responseCode = "200", description = "User updated")
    public UserDTO update(@PathParam("id") Long id, UserDTO dto) {
        var peb = User.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var u = (User) peb;
        u.name = dto.name();
        u.email = dto.email();
        return new UserDTO(u.id, u.name, u.email, u.createdAt);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a user")
    @APIResponse(responseCode = "204", description = "User deleted")
    public void delete(@PathParam("id") Long id) {
        User.findByIdOptional(id).orElseThrow(NotFoundException::new).delete();
    }
}