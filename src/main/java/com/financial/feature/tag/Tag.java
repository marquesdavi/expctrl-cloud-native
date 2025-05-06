package com.financial.feature.tag;

import com.financial.feature.user.User;
import com.financial.feature.transaction.entity.TransactionTag;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tag")
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(name = "tag_id"))
public class Tag extends PanacheEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @Column(name = "name", nullable = false)
    public String name;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    public List<TransactionTag> transactionTags;
}
