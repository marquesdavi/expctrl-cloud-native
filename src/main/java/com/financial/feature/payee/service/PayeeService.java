package com.financial.feature.payee.service;

import com.financial.feature.payee.dto.PayeeDTO;
import com.financial.feature.payee.entity.Payee;
import com.financial.feature.payee.repository.PayeeRepository;
import com.financial.feature.payee.service.contract.PayeeServiceContract;
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
public class PayeeService implements PayeeServiceContract {
    private final PayeeRepository payeeRepository;
    private final UserServiceContract userService;

    @Override
    public List<PayeeDTO> list() {
        return payeeRepository.listAll().stream()
                .map(peb -> {
                    var p = (Payee) peb;
                    return new PayeeDTO(p.id, p.user.id, p.name, p.details);
                })
                .toList();
    }

    @Override
    public PayeeDTO get(Long id) {
        Payee instance = findByID(id);
        return new PayeeDTO(instance.id, instance.user.id, instance.name, instance.details);
    }

    @Override
    @Transactional
    public Response create(PayeeDTO dto) {
        Payee p = new Payee();
        p.user    = userService.findByID(dto.userId());
        p.name    = dto.name();
        p.details = dto.details();
        payeeRepository.persist(p);
        return Response.created(URI.create("/payees/" + p.id)).build();
    }

    @Override
    @Transactional
    public PayeeDTO update(Long id, PayeeDTO dto) {
        Payee p = findByID(id);
        p.user    = userService.findByID(dto.userId());
        p.name    = dto.name();
        p.details = dto.details();
        payeeRepository.persist(p);
        return new PayeeDTO(p.id, p.user.id, p.name, p.details);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Payee instance = findByID(id);
        payeeRepository.delete(instance);
    }

    @Override
    public Payee findByID(Long id) {
        return payeeRepository.findByIdOptional(id)
                .orElseThrow(NotFoundException::new);
    }
}
