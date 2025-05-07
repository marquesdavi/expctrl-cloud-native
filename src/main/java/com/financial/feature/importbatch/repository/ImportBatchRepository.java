package com.financial.feature.importbatch.repository;

import com.financial.feature.importbatch.entity.ImportBatch;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ImportBatchRepository implements PanacheRepository<ImportBatch> {

}
