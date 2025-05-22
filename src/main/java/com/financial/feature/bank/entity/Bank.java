package com.financial.feature.bank.entity;

import com.financial.feature.account.entity.Account;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "bank")
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "code", nullable = false, unique = true)
    public String code;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    public List<Account> accounts;
}
