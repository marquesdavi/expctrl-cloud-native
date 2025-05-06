package com.financial.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transaction_tag")
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(name = "id"))
public class TransactionTag extends PanacheEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "transaction_id")
    public Transaction transaction;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tag_id")
    public Tag tag;
}
