package com.financial.resource;

import com.financial.dto.ImportBatchDTO;
import com.financial.entity.ImportBatch;
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


@Path("/import-batches")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImportBatchResource {

    @GET
    @Operation(summary = "List all import batches")
    @APIResponse(responseCode = "200", description = "Import batches retrieved")
    public List<ImportBatchDTO> list() {
        return ImportBatch.listAll().stream()
                .map(peb -> {
                    var b = (ImportBatch) peb;
                    return new ImportBatchDTO(
                            b.id, b.user.id, b.source, b.fileName, b.importedAt
                    );
                })
                .toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get import batch by ID")
    @APIResponse(responseCode = "200", description = "Import batch found")
    @APIResponse(responseCode = "404", description = "Import batch not found")
    public ImportBatchDTO get(@PathParam("id") Long id) {
        var peb = ImportBatch.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var b = (ImportBatch) peb;
        return new ImportBatchDTO(
                b.id, b.user.id, b.source, b.fileName, b.importedAt
        );
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new import batch")
    @APIResponse(responseCode = "201", description = "Import batch created")
    public Response create(ImportBatchDTO dto) {
        var b = new ImportBatch();
        b.user       = (User) User.findById(dto.userId());
        b.source     = dto.source();
        b.fileName   = dto.fileName();
        b.importedAt = dto.importedAt() != null ? dto.importedAt() : Instant.now();
        b.persist();
        return Response.created(URI.create("/import-batches/" + b.id)).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete an import batch")
    @APIResponse(responseCode = "204", description = "Import batch deleted")
    public void delete(@PathParam("id") Long id) {
        ImportBatch.findByIdOptional(id).orElseThrow(NotFoundException::new).delete();
    }
}

