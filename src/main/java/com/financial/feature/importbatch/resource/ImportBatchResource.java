package com.financial.feature.importbatch.resource;

import com.financial.feature.importbatch.dto.ImportBatchDTO;
import com.financial.feature.importbatch.service.contract.ImportBatchServiceContract;
import io.quarkus.security.Authenticated;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;


@Authenticated
@Path("/import-batches")
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ImportBatchResource {
    private final ImportBatchServiceContract importBatchService;

    @GET
    @Operation(summary = "List all import batches")
    @APIResponse(responseCode = "200", description = "Import batches retrieved")
    public List<ImportBatchDTO> list() {
        return importBatchService.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get import batch by ID")
    @APIResponse(responseCode = "200", description = "Import batch found")
    @APIResponse(responseCode = "404", description = "Import batch not found")
    public ImportBatchDTO get(@PathParam("id") Long id) {
        return importBatchService.get(id);
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new import batch")
    @APIResponse(responseCode = "201", description = "Import batch created")
    public Response create(ImportBatchDTO dto) {
        return importBatchService.create(dto);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete an import batch")
    @APIResponse(responseCode = "204", description = "Import batch deleted")
    public void delete(@PathParam("id") Long id) {
        importBatchService.delete(id);
    }
}

