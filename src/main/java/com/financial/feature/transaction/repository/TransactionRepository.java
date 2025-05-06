package com.financial.feature.transaction.repository;

import com.financial.feature.transaction.entity.Transaction;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

public class TransactionRepository implements PanacheRepository<Transaction> {
}
