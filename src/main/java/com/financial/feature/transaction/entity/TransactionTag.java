package com.financial.feature.transaction.entity;

import com.financial.feature.tag.entity.Tag;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "transaction_tag")
public class TransactionTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "transaction_id")
    public Transaction transaction;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tag_id")
    public Tag tag;
}
