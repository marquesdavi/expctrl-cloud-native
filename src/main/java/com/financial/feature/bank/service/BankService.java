package com.financial.feature.bank.service;

import com.financial.feature.bank.dto.BankDTO;
import com.financial.feature.bank.entity.Bank;
import com.financial.feature.bank.repository.BankRepository;
import com.financial.feature.bank.service.contract.BankServiceContract;
import jakarta.enterprise.context.RequestScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.List;

@RequestScoped
@RequiredArgsConstructor
public class BankService implements BankServiceContract {
    private final BankRepository bankRepository;

    @Override
    public List<BankDTO> list() {
        return bankRepository.listAll().stream()
                .map(b -> new BankDTO(b.id, b.name, b.code))
                .toList();
    }

    @Override
    public BankDTO get(Long id) {
        Bank bank = findById(id);
        return new BankDTO(bank.id, bank.name, bank.code);
    }

    @Override
    @Transactional
    public Response create(BankDTO dto) {
        var b = new Bank();
        b.name = dto.name();
        b.code = dto.code();
        bankRepository.persist(b);
        return Response.created(URI.create("/banks/" + b.id)).build();
    }

    @Override
    @Transactional
    public BankDTO update(Long id, BankDTO dto) {
        Bank bank = findById(id);
        bank.name = dto.name();
        bank.code = dto.code();
        return new BankDTO(bank.id, bank.name, bank.code);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Bank bank = findById(id);
        bankRepository.delete(bank);
    }

    @Override
    public Bank findById(Long id){
        return bankRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
    }
}
