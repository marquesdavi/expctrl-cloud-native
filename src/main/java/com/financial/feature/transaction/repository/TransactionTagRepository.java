package com.financial.feature.transaction.repository;

import com.financial.feature.transaction.entity.TransactionTag;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TransactionTagRepository implements PanacheRepository<TransactionTag> {

    @Transactional
    public void persistAll(List<TransactionTag> tags) {
        persist(tags);
        getEntityManager().flush();
        getEntityManager().clear();
    }
}
