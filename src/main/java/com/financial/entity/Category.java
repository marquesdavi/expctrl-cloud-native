package com.financial.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "category")
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(name = "category_id"))
public class Category extends PanacheEntity {
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
