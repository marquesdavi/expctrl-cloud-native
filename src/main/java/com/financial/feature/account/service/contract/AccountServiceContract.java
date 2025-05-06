package com.financial.feature.account.service.contract;

import com.financial.feature.account.dto.AccountDTO;
import com.financial.feature.account.entity.Account;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface AccountServiceContract {
    List<AccountDTO> list();
    AccountDTO get(Long id);
    Response create(AccountDTO dto);
    AccountDTO update(Long id, AccountDTO dto);
    void delete(Long id);
    Account findById(Long id);
}
