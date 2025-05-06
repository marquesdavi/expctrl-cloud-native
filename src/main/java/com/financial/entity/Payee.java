package com.financial.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "payee")
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(name = "payee_id"))
public class Payee extends PanacheEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @Column(name = "name", nullable = false)
    public String name;

    public String details;

    @OneToMany(mappedBy = "payee", cascade = CascadeType.ALL)
    public List<Transaction> transactions;
}
