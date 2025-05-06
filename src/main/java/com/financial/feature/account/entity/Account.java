package com.financial.feature.account.entity;

import com.financial.feature.bank.Bank;
import com.financial.feature.transaction.entity.Transaction;
import com.financial.feature.user.User;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "account")
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(name = "account_id"))
public class Account extends PanacheEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "bank_id")
    public Bank bank;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @Column(name = "account_number", nullable = false)
    public String accountNumber;

    public String branch;

    @Column(columnDefinition = "char(3) default 'BRL'")
    public String currency;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    public List<Transaction> transactions;
}
