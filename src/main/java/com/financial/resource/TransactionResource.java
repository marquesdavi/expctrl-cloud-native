// src/main/java/com/financial/expctrl/resource/TransactionResource.java
package com.financial.resource;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import com.financial.dto.TransactionDTO;
import com.financial.entity.*;


@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {

    @GET
    @Operation(summary = "List all transactions")
    @APIResponse(responseCode = "200", description = "Transactions retrieved")
    public List<TransactionDTO> list() {
        return Transaction.listAll().stream()
                .map(peb -> toDTO((Transaction) peb))
                .toList();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get transaction by ID")
    @APIResponse(responseCode = "200", description = "Transaction found")
    @APIResponse(responseCode = "404", description = "Transaction not found")
    public TransactionDTO get(@PathParam("id") Long id) {
        var peb = Transaction.findByIdOptional(id).orElseThrow(NotFoundException::new);
        return toDTO((Transaction) peb);
    }

    @POST
    @Transactional
    @Operation(summary = "Create a new transaction")
    @APIResponse(responseCode = "201", description = "Transaction created")
    public Response create(TransactionDTO dto) {
        var t = new Transaction();
        t.account = (Account) Account.findById(dto.accountId());
        t.date = dto.date();
        t.amount = dto.amount();
        t.type = dto.type();
        t.description = dto.description();
        t.details = dto.details();
        t.documentNumber = dto.documentNumber();
        t.runningBalance = dto.runningBalance();
        t.category = (Category) Category.findById(dto.categoryId());
        t.payee = (Payee) Payee.findById(dto.payeeId());
        t.importBatch = (ImportBatch) ImportBatch.findById(dto.importBatchId());
        t.persist();

        if (dto.tagIds() != null) {
            dto.tagIds().forEach(tagId -> {
                var tt = new TransactionTag();
                tt.transaction = t;
                tt.tag = (Tag) Tag.findById(tagId);
                tt.persist();
            });
        }
        return Response.created(URI.create("/transactions/" + t.id)).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing transaction")
    @APIResponse(responseCode = "200", description = "Transaction updated")
    public TransactionDTO update(@PathParam("id") Long id, TransactionDTO dto) {
        var peb = Transaction.findByIdOptional(id).orElseThrow(NotFoundException::new);
        var t = (Transaction) peb;
        t.account = (Account) Account.findById(dto.accountId());
        t.date = dto.date();
        t.amount = dto.amount();
        t.type = dto.type();
        t.description = dto.description();
        t.details = dto.details();
        t.documentNumber = dto.documentNumber();
        t.runningBalance = dto.runningBalance();
        t.category = (Category) Category.findById(dto.categoryId());
        t.payee = (Payee) Payee.findById(dto.payeeId());
        t.importBatch = (ImportBatch) ImportBatch.findById(dto.importBatchId());

        TransactionTag.delete("transaction", t);
        if (dto.tagIds() != null) {
            dto.tagIds().forEach(tagId -> {
                var tt = new TransactionTag();
                tt.transaction = t;
                tt.tag = (Tag) Tag.findById(tagId);
                tt.persist();
            });
        }
        return toDTO(t);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a transaction")
    @APIResponse(responseCode = "204", description = "Transaction deleted")
    public void delete(@PathParam("id") Long id) {
        Transaction.findByIdOptional(id).orElseThrow(NotFoundException::new).delete();
    }

    private TransactionDTO toDTO(Transaction t) {
        return new TransactionDTO(
                t.id,
                t.account.id,
                t.date,
                t.amount,
                t.type,
                t.description,
                t.details,
                t.documentNumber,
                t.runningBalance,
                t.category.id,
                t.payee.id,
                t.importBatch.id,
                t.transactionTags.stream().map(tt -> tt.tag.id).toList()
        );
    }
}

