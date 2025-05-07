package com.financial.feature.bank.entity;

import com.financial.feature.account.entity.Account;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "bank")
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(name = "bank_id"))
public class Bank extends PanacheEntity {
    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "code", nullable = false, unique = true)
    public String code;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    public List<Account> accounts;
}
