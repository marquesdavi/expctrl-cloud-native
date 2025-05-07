package com.financial.feature.bank.service.contract;

import com.financial.feature.account.dto.AccountDTO;
import com.financial.feature.account.entity.Account;
import com.financial.feature.bank.dto.BankDTO;
import com.financial.feature.bank.entity.Bank;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface BankServiceContract {
    List<BankDTO> list();
    BankDTO get(Long id);
    Response create(BankDTO dto);
    BankDTO update(Long id, BankDTO dto);
    void delete(Long id);
    Bank findById(Long id);
}
