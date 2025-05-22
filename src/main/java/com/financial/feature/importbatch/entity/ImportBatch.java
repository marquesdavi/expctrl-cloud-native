package com.financial.feature.importbatch.entity;

import com.financial.feature.transaction.entity.Transaction;
import com.financial.feature.user.entity.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name = "import_batch")
public class ImportBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Long id;

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
