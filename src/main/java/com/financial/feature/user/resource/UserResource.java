package com.financial.feature.user.resource;


import com.financial.feature.user.dto.UserDTO;
import com.financial.feature.user.dto.UserRequest;
import com.financial.feature.user.service.contract.UserServiceContract;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@Path("/users")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserServiceContract userService;

    @GET
    @Operation(summary = "List all users")
    @APIResponse(responseCode = "200", description = "Users retrieved")
    public List<UserDTO> list() {
        return userService.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get user by ID")
    @APIResponse(responseCode = "200", description = "User found")
    @APIResponse(responseCode = "404", description = "User not found")
    public UserDTO get(@PathParam("id") Long id) {
        return userService.get(id);
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new user")
    @APIResponse(responseCode = "201", description = "User created")
    public Response create(UserRequest dto) {
        return userService.create(dto);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing user")
    @APIResponse(responseCode = "200", description = "User updated")
    public UserDTO update(@PathParam("id") Long id, UserDTO dto) {
        return userService.update(id, dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a user")
    @APIResponse(responseCode = "204", description = "User deleted")
    public void delete(@PathParam("id") Long id) {
        userService.delete(id);
    }
}