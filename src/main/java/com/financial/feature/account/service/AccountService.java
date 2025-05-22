package com.financial.feature.account.service;

import com.financial.feature.account.dto.AccountDTO;
import com.financial.feature.account.entity.Account;
import com.financial.feature.account.repository.AccountRepository;
import com.financial.feature.account.service.contract.AccountServiceContract;
import com.financial.feature.bank.service.contract.BankServiceContract;
import com.financial.feature.user.service.contract.UserServiceContract;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

@RequestScoped
@RequiredArgsConstructor
public class AccountService implements AccountServiceContract {
    private final AccountRepository accountRepository;
    private final UserServiceContract userService;
    private final BankServiceContract bankService;


    @Override
    public List<AccountDTO> list() {
        return accountRepository.listAll().stream()
                .map(a -> new AccountDTO(
                        a.id,
                        a.bank.id,
                        a.user.id,
                        a.accountNumber,
                        a.branch,
                        a.currency
                ))
                .toList();
    }

    @Override
    public AccountDTO get(Long id) {
        Account a = findById(id);

        return new AccountDTO(
                a.id,
                a.bank.id,
                a.user.id,
                a.accountNumber,
                a.branch,
                a.currency
        );
    }

    @Override
    @Transactional
    public Response create(AccountDTO dto) {
        Account account = new Account();
        buildAccount(dto, account);
        accountRepository.persist(account);
        return Response.created(URI.create("/accounts/" + account.id)).build();
    }

    private void buildAccount(AccountDTO dto, Account account) {
        account.bank = bankService.findById(dto.bankId());
        account.user = userService.findByID(dto.userId());
        account.accountNumber = dto.accountNumber();
        account.branch        = dto.branch();
        account.currency      = dto.currency();
    }

    @Override
    @Transactional
    public AccountDTO update(Long id, AccountDTO dto) {
        Account account = findById(id);
        buildAccount(dto, account);
        accountRepository.persist(account);
        return new AccountDTO(
                account.id,
                account.bank.id,
                account.user.id,
                account.accountNumber,
                account.branch,
                account.currency
        );
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Account account = accountRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);

        accountRepository.delete(account);
    }

    @Override
    public Account findById(Long id){
        return accountRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
    }
}
