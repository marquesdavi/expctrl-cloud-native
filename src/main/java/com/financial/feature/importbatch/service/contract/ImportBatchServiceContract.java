package com.financial.feature.importbatch.service.contract;

import com.financial.feature.importbatch.dto.ImportBatchDTO;
import com.financial.feature.importbatch.entity.ImportBatch;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface ImportBatchServiceContract {
    List<ImportBatchDTO> list();
    ImportBatchDTO get(Long id);
    Response create(ImportBatchDTO dto);
    void delete(Long id);
    ImportBatch findById(Long id);
}
