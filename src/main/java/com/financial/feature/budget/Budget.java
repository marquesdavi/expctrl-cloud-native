package com.financial.feature.budget;

import com.financial.feature.category.Category;
import com.financial.feature.user.User;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "budget")
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(name = "budget_id"))
public class Budget extends PanacheEntity {
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
