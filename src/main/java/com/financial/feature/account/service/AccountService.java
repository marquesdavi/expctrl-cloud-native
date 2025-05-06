package com.financial.feature.account.service;

import com.financial.feature.account.entity.Account;
import com.financial.feature.account.dto.AccountDTO;
import com.financial.feature.account.repository.AccountRepository;
import com.financial.feature.account.service.contract.AccountServiceContract;
import com.financial.feature.bank.Bank;
import com.financial.feature.user.User;
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
        Account a = new Account();
        a.bank = (Bank) Bank.findById(dto.bankId());
        a.user = (User) User.findById(dto.userId());
        a.accountNumber = dto.accountNumber();
        a.branch        = dto.branch();
        a.currency      = dto.currency();
        accountRepository.persist(a);
        return Response.created(URI.create("/accounts/" + a.id)).build();
    }

    @Override
    @Transactional
    public AccountDTO update(Long id, AccountDTO dto) {
        var a = findById(id);
        a.bank          = (Bank) Bank.findById(dto.bankId());
        a.user          = (User) User.findById(dto.userId());
        a.accountNumber = dto.accountNumber();
        a.branch        = dto.branch();
        a.currency      = dto.currency();
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
    public void delete(Long id) {
        accountRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new)
                .delete();
    }

    @Override
    public Account findById(Long id){
        return accountRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
    }
}
