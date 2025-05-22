package com.financial.feature.account.entity;

import com.financial.feature.bank.entity.Bank;
import com.financial.feature.transaction.entity.Transaction;
import com.financial.feature.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

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
