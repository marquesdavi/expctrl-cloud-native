package com.financial.resource;

import com.financial.entity.User;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import java.net.URI;
import java.util.List;
import com.financial.dto.PayeeDTO;
import com.financial.entity.Payee;

@Path("/payees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PayeeResource {

    @GET
    @Operation(summary = "List all payees")
    @APIResponse(responseCode = "200", description = "Payees retrieved")
    public List<PayeeDTO> list() {
        return Payee.listAll().stream()
                .map(peb -> {
                    var p = (Payee) peb;
                    return new PayeeDTO(p.id, p.user.id, p.name, p.details);
                })
                .toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get payee by ID")
    @APIResponse(responseCode = "200", description = "Payee found")
    @APIResponse(responseCode = "404", description = "Payee not found")
    public PayeeDTO get(@PathParam("id") Long id) {
        var peb = Payee.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var p = (Payee) peb;
        return new PayeeDTO(p.id, p.user.id, p.name, p.details);
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new payee")
    @APIResponse(responseCode = "201", description = "Payee created")
    public Response create(PayeeDTO dto) {
        var p = new Payee();
        p.user    = (User) User.findById(dto.userId());
        p.name    = dto.name();
        p.details = dto.details();
        p.persist();
        return Response.created(URI.create("/payees/" + p.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing payee")
    @APIResponse(responseCode = "200", description = "Payee updated")
    public PayeeDTO update(@PathParam("id") Long id, PayeeDTO dto) {
        var peb = Payee.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var p = (Payee) peb;
        p.user    = (User) User.findById(dto.userId());
        p.name    = dto.name();
        p.details = dto.details();
        return new PayeeDTO(p.id, p.user.id, p.name, p.details);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a payee")
    @APIResponse(responseCode = "204", description = "Payee deleted")
    public void delete(@PathParam("id") Long id) {
        Payee.findByIdOptional(id).orElseThrow(NotFoundException::new).delete();
    }
}

