package com.financial.feature.payee.entity;

import com.financial.feature.transaction.entity.Transaction;
import com.financial.feature.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "payee")
public class Payee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @Column(name = "name", nullable = false)
    public String name;

    public String details;

    @OneToMany(mappedBy = "payee", cascade = CascadeType.ALL)
    public List<Transaction> transactions;
}
