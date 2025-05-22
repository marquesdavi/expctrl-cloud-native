package com.financial.feature.tag.entity;

import com.financial.feature.transaction.entity.TransactionTag;
import com.financial.feature.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @Column(name = "name", nullable = false)
    public String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    public List<TransactionTag> transactionTags;
}
