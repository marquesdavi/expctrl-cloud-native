package com.financial.feature.payee.repository;

import com.financial.feature.payee.entity.Payee;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PayeeRepository implements PanacheRepository<Payee> {
}
