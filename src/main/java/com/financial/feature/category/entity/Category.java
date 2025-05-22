package com.financial.feature.category.entity;

import com.financial.feature.transaction.entity.Transaction;
import com.financial.feature.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "type", nullable = false)
    public String type;

    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    public Category parentCategory;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    public List<Transaction> transactions;
}
