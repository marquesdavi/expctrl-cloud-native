package com.financial.feature.importbatch.entity;

import com.financial.feature.transaction.entity.Transaction;
import com.financial.feature.user.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "import_batch")
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(name = "import_batch_id"))
public class ImportBatch extends PanacheEntity {
    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    public String source;

    @Column(name = "file_name")
    public String fileName;

    @Column(name = "imported_at")
    public Instant importedAt;

    @OneToMany(mappedBy = "importBatch", cascade = CascadeType.ALL)
    public List<Transaction> transactions;
}
