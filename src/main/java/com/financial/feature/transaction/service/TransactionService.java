package com.financial.feature.transaction.service;

import com.financial.feature.account.service.contract.AccountServiceContract;
import com.financial.feature.category.Category;
import com.financial.feature.importbatch.ImportBatch;
import com.financial.feature.payee.entity.Payee;
import com.financial.feature.tag.service.contract.TagServiceContract;
import com.financial.feature.transaction.entity.Transaction;
import com.financial.feature.transaction.dto.TransactionDTO;
import com.financial.feature.transaction.entity.TransactionTag;
import com.financial.feature.transaction.repository.TransactionRepository;
import com.financial.feature.transaction.repository.TransactionTagRepository;
import com.financial.feature.transaction.service.contract.TransactionServiceContract;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;


@RequestScoped
@RequiredArgsConstructor
public class TransactionService implements TransactionServiceContract {
    private final TransactionRepository transactionRepository;
    private final TransactionTagRepository transactionTagRepository;
    private final AccountServiceContract accountService;
    private final TagServiceContract tagService;

    @Override
    public List<TransactionDTO> list() {
        return transactionRepository.listAll().stream()
                .map(this::toDTO)
                .toList();
    }

    @Override
    public TransactionDTO get(Long id) {
        return toDTO(findByID(id));
    }

    @Override
    @Transactional
    public Response create(TransactionDTO dto) {
        Transaction instance = new Transaction();
        mapTransactionModification(dto, instance);
        transactionRepository.persist(instance);

        handleTransactionTags(dto, instance);
        return Response.created(URI.create("/transactions/" + instance.id)).build();
    }

    @Override
    @Transactional
    public TransactionDTO update(Long id, TransactionDTO dto) {
        Transaction instance = findByID(id);
        mapTransactionModification(dto, instance);

        TransactionTag.delete("transaction", instance);
        handleTransactionTags(dto, instance);
        return toDTO(instance);
    }

    private void mapTransactionModification(TransactionDTO dto, Transaction instance) {
        instance.account = accountService.findById(dto.accountId());
        instance.date = dto.date();
        instance.amount = dto.amount();
        instance.type = dto.type();
        instance.description = dto.description();
        instance.details = dto.details();
        instance.documentNumber = dto.documentNumber();
        instance.runningBalance = dto.runningBalance();
        instance.category = (Category) Category.findById(dto.categoryId());
        instance.payee = (Payee) Payee.findById(dto.payeeId());
        instance.importBatch = (ImportBatch) ImportBatch.findById(dto.importBatchId());
    }

    @Transactional
    private void handleTransactionTags(TransactionDTO dto, Transaction instance) {
        if (dto.tagIds() != null) {
            List<TransactionTag> transactionTags = dto.tagIds().stream().map(tagId -> {
                var transactionTag = new TransactionTag();
                transactionTag.transaction = instance;
                transactionTag.tag = tagService.findByID(tagId);
                return transactionTag;
            }).toList();

            transactionTagRepository.persistAll(transactionTags);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Transaction instance = findByID(id);
        transactionRepository.delete(instance);
    }

    @Override
    public Transaction findByID(Long id) {
        return transactionRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
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
