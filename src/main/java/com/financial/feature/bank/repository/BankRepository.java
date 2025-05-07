package com.financial.feature.bank.repository;

import com.financial.feature.bank.entity.Bank;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BankRepository implements PanacheRepository<Bank> {

}
