package com.financial.feature.transaction.service.contract;

import com.financial.feature.transaction.entity.Transaction;
import com.financial.feature.transaction.dto.TransactionDTO;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface TransactionServiceContract {
    List<TransactionDTO> list();
    TransactionDTO get(Long id);
    Response create(TransactionDTO dto);
    TransactionDTO update(Long id, TransactionDTO dto);
    void delete(Long id);
    Transaction findByID(Long id);
}
