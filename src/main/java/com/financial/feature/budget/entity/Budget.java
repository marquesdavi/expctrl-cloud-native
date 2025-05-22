package com.financial.feature.budget.entity;

import com.financial.feature.category.entity.Category;
import com.financial.feature.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "budget")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id")
    public Category category;

    @Column(name = "period_start", nullable = false)
    public LocalDate periodStart;

    @Column(name = "period_end",   nullable = false)
    public LocalDate periodEnd;

    @Column(name = "amount",       nullable = false)
    public BigDecimal amount;
}
