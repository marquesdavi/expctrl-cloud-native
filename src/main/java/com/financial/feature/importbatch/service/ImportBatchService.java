package com.financial.feature.importbatch.service;

import com.financial.feature.importbatch.dto.ImportBatchDTO;
import com.financial.feature.importbatch.entity.ImportBatch;
import com.financial.feature.importbatch.repository.ImportBatchRepository;
import com.financial.feature.importbatch.service.contract.ImportBatchServiceContract;
import com.financial.feature.user.entity.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.time.Instant;
import java.util.List;

@RequestScoped
@RequiredArgsConstructor
public class ImportBatchService implements ImportBatchServiceContract {
    private final ImportBatchRepository importBatchRepository;

    @Override
    public List<ImportBatchDTO> list() {
        return importBatchRepository.listAll().stream()
                .map(b -> new ImportBatchDTO(
                        b.id, b.user.id, b.source, b.fileName, b.importedAt
                ))
                .toList();
    }

    @Override
    public ImportBatchDTO get(Long id) {
        ImportBatch b = findById(id);
        return new ImportBatchDTO(
                b.id, b.user.id, b.source, b.fileName, b.importedAt
        );
    }

    @Override
    @Transactional
    public Response create(ImportBatchDTO dto) {
        ImportBatch b = new ImportBatch();
        b.user       = (User) User.findById(dto.userId());
        b.source     = dto.source();
        b.fileName   = dto.fileName();
        b.importedAt = dto.importedAt() != null ? dto.importedAt() : Instant.now();
        b.persist();
        return Response.created(URI.create("/import-batches/" + b.id)).build();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        ImportBatch batch = findById(id);
        importBatchRepository.delete(batch);
    }

    @Override
    public ImportBatch findById(Long id) {
        return importBatchRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
    }
}
