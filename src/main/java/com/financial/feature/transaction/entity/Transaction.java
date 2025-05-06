package com.financial.feature.transaction.entity;

import com.financial.feature.account.entity.Account;
import com.financial.feature.category.Category;
import com.financial.feature.importbatch.ImportBatch;
import com.financial.feature.payee.entity.Payee;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "transaction")
@Access(AccessType.FIELD)
@AttributeOverride(name = "id", column = @Column(name = "transaction_id"))
public class Transaction extends PanacheEntity {
    @ManyToOne(optional = false)
    @JoinColumn(name = "account_id")
    public Account account;

    @Column(name = "date",       nullable = false)
    public LocalDate date;

    @Column(name = "type")
    public String type;

    @Column(name = "amount",     nullable = false)
    public BigDecimal amount;

    public String description;
    public String details;

    @Column(name = "document_number")
    public String documentNumber;

    @Column(name = "running_balance")
    public BigDecimal runningBalance;

    @ManyToOne
    @JoinColumn(name = "category_id")
    public Category category;

    @ManyToOne
    @JoinColumn(name = "payee_id")
    public Payee payee;

    @ManyToOne
    @JoinColumn(name = "import_batch_id")
    public ImportBatch importBatch;

    @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
    public List<TransactionTag> transactionTags;
}
