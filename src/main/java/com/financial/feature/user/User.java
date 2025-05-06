package com.financial.feature.user;

import com.financial.feature.account.entity.Account;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "app_user")
@Access(AccessType.FIELD)
@AttributeOverrides({
        @AttributeOverride(name = "id",        column = @Column(name = "user_id")),
        @AttributeOverride(name = "createdAt", column = @Column(name = "created_at"))
})
public class User extends PanacheEntity {
    @Column(name = "name",  nullable = false)
    public String name;

    @Column(name = "email", nullable = false, unique = true)
    public String email;

    public Instant createdAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    public List<Account> accounts;
}
